package de.gravitex.bpm.helper.listener.base;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.TaskListener;

import de.gravitex.bpm.helper.util.ProcessHelper;

public abstract class ExtendedTaskListener implements TaskListener {

	protected String getMasterProcessBusinessKey(DelegateExecution execution) {
		return ProcessHelper.getMasterProcessBusinessKey(execution);
	}
}