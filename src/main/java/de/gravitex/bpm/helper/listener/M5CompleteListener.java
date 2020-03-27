package de.gravitex.bpm.helper.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import de.gravitex.bpm.helper.constant.ProcessConstants;

public class M5CompleteListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.getProcessEngine().getRuntimeService().correlateMessage(ProcessConstants.Slave.MSG.MSG_CALL_S5);
	}
}