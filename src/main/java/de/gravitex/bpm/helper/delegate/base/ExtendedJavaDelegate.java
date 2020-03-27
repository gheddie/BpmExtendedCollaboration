package de.gravitex.bpm.helper.delegate.base;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpm.helper.ProcessRegistry;
import de.gravitex.bpm.helper.util.ProcessHelper;

public abstract class ExtendedJavaDelegate implements JavaDelegate {

	protected ProcessRegistry getProcessRegistry(DelegateExecution execution) {
		return ProcessHelper.getProcessRegistry(execution.getProcessEngine().getRuntimeService(), execution.getId());
	}
}