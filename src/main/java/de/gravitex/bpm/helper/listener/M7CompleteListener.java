package de.gravitex.bpm.helper.listener;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.ProcessDataItem;

public class M7CompleteListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		RuntimeService runtimeService = delegateTask.getProcessEngine().getRuntimeService();
		ProcessDataItem item = (ProcessDataItem) runtimeService.getVariable(delegateTask.getExecution().getId(),
				ProcessConstants.Main.VAR.VAR_PROCESS_DATA_ITEM);
		runtimeService.correlateMessage(ProcessConstants.AnotherSlave.MSG.MSG_FINISH_AS,
				findAnotherSlaveInstance(runtimeService, delegateTask.getExecution().getBusinessKey(), item)
						.getBusinessKey());
	}

	private ProcessInstance findAnotherSlaveInstance(RuntimeService runtimeService, String parentBusinessKey,
			ProcessDataItem item) {
		// 'another slaves' belonging to master...
		List<ProcessInstance> anotherSlaveInstance = runtimeService.createProcessInstanceQuery()
				.variableValueEquals(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK, parentBusinessKey)
				.processDefinitionKey(ProcessConstants.AnotherSlave.DEF.DEF_ANOTHER_SLAVE_PROCESS).list();
		// check for equal process item...
		for (ProcessInstance processInstance : anotherSlaveInstance) {
			if (runtimeService.getVariable(processInstance.getId(), ProcessConstants.AnotherSlave.VAR.VAR_ANOTHER_SLAVE_ITEM).equals(item)) {
				return processInstance;
			}
		}
		return null;
	}
}