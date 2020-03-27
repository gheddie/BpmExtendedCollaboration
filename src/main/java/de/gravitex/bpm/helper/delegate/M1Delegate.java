package de.gravitex.bpm.helper.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.delegate.base.ExtendedJavaDelegate;

public class M1Delegate extends ExtendedJavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		getProcessRegistry(execution).startProcessInstanceByMessage(execution, ProcessConstants.Slave.MSG.MSG_CALL_B);
	}
}