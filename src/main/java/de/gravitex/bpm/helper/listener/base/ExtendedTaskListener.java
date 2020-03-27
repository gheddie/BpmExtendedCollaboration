package de.gravitex.bpm.helper.listener.base;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.ProcessRegistry;
import de.gravitex.bpm.helper.util.ProcessHelper;

public abstract class ExtendedTaskListener implements TaskListener {

	protected ProcessRegistry getProcessRegistry(DelegateTask delegateTask) {
		return ProcessHelper.getProcessRegistry(delegateTask.getProcessEngine().getRuntimeService(),
				delegateTask.getExecutionId());
	}
	
	protected void registerCallback(DelegateTask delegateTask, ProcessInstance processInstance, String callbackId, String targetMessageName) {
		getProcessRegistry(delegateTask).registerCallback(processInstance.getId(),
				callbackId, targetMessageName,
				delegateTask.getExecution().getBusinessKey());
	}
}