package de.gravitex.bpm.helper.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.listener.base.ExtendedTaskListener;

public class M2CompleteListener extends ExtendedTaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		ProcessInstance processInstance = getProcessRegistry(delegateTask).startProcessInstanceByMessage(delegateTask.getExecution(),
				ProcessConstants.Slave.MSG.MSG_CALL_A);
		registerCallback(delegateTask, processInstance, ProcessConstants.Slave.CALLBACK.CALLBACK_S4,
				ProcessConstants.Main.MSG.MSG_RECALL_M5);
	}
}