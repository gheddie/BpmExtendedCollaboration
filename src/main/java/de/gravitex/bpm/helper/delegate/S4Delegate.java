package de.gravitex.bpm.helper.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.delegate.base.ExtendedJavaDelegate;

public class S4Delegate extends ExtendedJavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		getProcessRegistry(execution).getCallback(execution.getId(), ProcessConstants.Slave.CALLBACK.CALLBACK_S4)
				.execute(execution);
	}
}