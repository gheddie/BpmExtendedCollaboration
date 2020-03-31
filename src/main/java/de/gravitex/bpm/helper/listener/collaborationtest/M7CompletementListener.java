package de.gravitex.bpm.helper.listener.collaborationtest;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.entity.collaborationtest.ProcessDataItem;
import de.gravitex.bpm.helper.util.businesskey.matcher.BusinessKeyMatcher;

public class M7CompletementListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		RuntimeService runtimeService = delegateTask.getProcessEngine().getRuntimeService();
		ProcessDataItem item = (ProcessDataItem) runtimeService.getVariable(delegateTask.getExecution().getId(),
				ProcessConstants.Collaboration.Main.VAR.VAR_PROCESS_DATA_ITEM);
		String parentBusinessKey = delegateTask.getExecution().getBusinessKey();
		ProcessInstance matchingAnotherSlave = BusinessKeyMatcher
				.forProcessDefinitionKey(ProcessConstants.Collaboration.AnotherSlave.DEF.DEF_ANOTHER_SLAVE_PROCESS)
				.withParentBusinessKey(parentBusinessKey).withAdditionalValue(item.getValue())
				.singleResult(runtimeService);
		runtimeService.correlateMessage(ProcessConstants.Collaboration.AnotherSlave.MSG.MSG_FINISH_AS,
				matchingAnotherSlave.getBusinessKey());
	}
}