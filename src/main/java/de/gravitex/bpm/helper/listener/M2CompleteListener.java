package de.gravitex.bpm.helper.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.listener.base.ExtendedTaskListener;

public class M2CompleteListener extends ExtendedTaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.getProcessEngine().getRuntimeService()
				.startProcessInstanceByMessage(ProcessConstants.Slave.MSG.MSG_CALL_A);
	}
}