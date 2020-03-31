package de.gravitex.bpm.helper.listener.base;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import de.gravitex.bpm.helper.entity.traindepartmentnew.TrainDepartmentData;
import de.gravitex.bpm.helper.util.ProcessHelper;

public abstract class ExtendedExecutionListener implements ExecutionListener {

	protected TrainDepartmentData getTrainDepartmentData(DelegateExecution execution) {
		return ProcessHelper.getgetTrainDepartmentData(execution);
	}
}