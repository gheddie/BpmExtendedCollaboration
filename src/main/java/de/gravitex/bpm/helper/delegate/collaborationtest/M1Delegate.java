package de.gravitex.bpm.helper.delegate.collaborationtest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpm.helper.constant.ProcessConstants;

public class M1Delegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.getProcessEngine().getRuntimeService()
				.startProcessInstanceByMessage(ProcessConstants.Collaboration.Slave.MSG.MSG_CALL_B);
	}
}