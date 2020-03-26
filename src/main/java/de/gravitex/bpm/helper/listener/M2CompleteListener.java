package de.gravitex.bpm.helper.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import de.gravitex.bpm.helper.ProcessRegistry;
import de.gravitex.bpm.helper.constant.ProcessConstants;

public class M2CompleteListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		ProcessRegistry.getInstance().startProcessInstanceByMessage(delegateTask.getExecution(),
				ProcessConstants.Slave.MSG.MSG_CALL, ProcessConstants.Slave.CALLBACK.CALLBACK_MSG_RECALL, ProcessConstants.Main.MSG.MSG_RECALL);
	}
}