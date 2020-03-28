package de.gravitex.bpm.helper.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class M6Delegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ProcessHelper.startProcessInstanceByMessage(execution.getProcessEngine(),
				ProcessConstants.AnotherSlave.DEF.DEF_ANOTHER_SLAVE_PROCESS,
				ProcessConstants.AnotherSlave.MSG.MSG_START_ANOTHER_SLAVE, null);
	}
}