package de.gravitex.bpm.helper.listener.base;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import de.gravitex.bpm.helper.entity.traindepartmentnew.TrainDepartureData;
import de.gravitex.bpm.helper.util.ProcessHelper;

public abstract class ExtendedExecutionListener implements ExecutionListener {

	protected TrainDepartureData getTrainDepartmentData(DelegateExecution execution) {
		return ProcessHelper.getTrainDepartureData(execution);
	}
}