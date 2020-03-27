package de.gravitex.bpm.helper.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.delegate.base.SlaveProcessDelegate;

public class S4Delegate extends SlaveProcessDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.getProcessEngine().getRuntimeService().correlateMessage(
				ProcessConstants.Main.MSG.MSG_RECALL_M5, getMasterProcessBusinessKey(execution));
	}
}