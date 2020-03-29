package de.gravitex.bpm.helper.listener.base;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;

public abstract class MasterProcessTaskListener implements TaskListener {

	protected ProcessInstance resolveSlaveProcess(DelegateTask delegateTask) {
		ProcessInstance processInstance = delegateTask.getProcessEngine().getRuntimeService()
				.createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Collaboration.Slave.DEF.DEF_SLAVE_PROCESS)
				.variableValueEquals(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK,
						delegateTask.getExecution().getBusinessKey())
				.singleResult();
		return processInstance;
	}
}