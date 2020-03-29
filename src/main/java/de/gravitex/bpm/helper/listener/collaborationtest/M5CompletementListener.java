package de.gravitex.bpm.helper.listener.collaborationtest;

import org.camunda.bpm.engine.delegate.DelegateTask;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.listener.base.MasterProcessTaskListener;

public class M5CompletementListener extends MasterProcessTaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.getProcessEngine().getRuntimeService().correlateMessage(
				ProcessConstants.Slave.MSG.MSG_CALL_S5,
				resolveSlaveProcess(delegateTask).getBusinessKey());
	}
}