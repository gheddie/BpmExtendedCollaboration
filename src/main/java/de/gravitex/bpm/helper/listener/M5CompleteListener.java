package de.gravitex.bpm.helper.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.listener.base.MasterProcessTaskListener;

public class M5CompleteListener extends MasterProcessTaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.getProcessEngine().getRuntimeService().correlateMessage(
				ProcessConstants.Slave.MSG.MSG_CALL_S5,
				resolveSlaveProcess(delegateTask).getBusinessKey());
	}
}