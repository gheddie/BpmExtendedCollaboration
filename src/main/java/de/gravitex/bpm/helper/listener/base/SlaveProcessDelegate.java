package de.gravitex.bpm.helper.listener.base;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpm.helper.constant.ProcessConstants;

public abstract class SlaveProcessDelegate implements JavaDelegate {

	protected String getMasterProcessBusinessKey(DelegateExecution execution) {
		return (String) execution.getProcessEngine().getRuntimeService()
				.getVariable(execution.getId(), ProcessConstants.Slave.VAR.VAR_MASTER_PROCESS_BK);
	}
}