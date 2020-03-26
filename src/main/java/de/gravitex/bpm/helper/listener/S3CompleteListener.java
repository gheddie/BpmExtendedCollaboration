package de.gravitex.bpm.helper.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import de.gravitex.bpm.helper.ProcessCallback;
import de.gravitex.bpm.helper.ProcessRegistry;
import de.gravitex.bpm.helper.constant.ProcessConstants;

public class S3CompleteListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		String executionId = delegateTask.getExecution().getId();
		ProcessCallback callback = ProcessRegistry.getInstance().getCallback(executionId,
				ProcessConstants.Slave.CALLBACK.CALLBACK_MSG_RECALL);
		callback.execute(delegateTask);
	}
}