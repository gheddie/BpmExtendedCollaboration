package de.gravitex.bpm.helper.delegate.collaborationtest;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.delegate.base.ExtendedJavaDelegate;

public class S4Delegate extends ExtendedJavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.getProcessEngine().getRuntimeService().correlateMessage(
				ProcessConstants.Collaboration.Main.MSG.MSG_RECALL_M5, getMasterProcessBusinessKey(execution));
	}
}