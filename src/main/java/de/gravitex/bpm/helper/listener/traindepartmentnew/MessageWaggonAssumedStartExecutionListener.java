package de.gravitex.bpm.helper.listener.traindepartmentnew;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.listener.base.ExtendedExecutionListener;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;

public class MessageWaggonAssumedStartExecutionListener extends ExtendedExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		Waggon assumedWaggon = (Waggon) execution.getVariable(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_SINGLE_WAGGON_TO_ASSUME);
		if (assumedWaggon.getRepairDurationInHours() == null) {
			throw new BpmnError(ProcessConstants.Trainpartment.TrainStation.ERROR.ERR_INVALID_WAGGON_ASSUMPTION);
		}
		// update repair time for waggon in process data
		getTrainDepartmentData(execution).getWaggonByWaggonNumber(assumedWaggon.getWaggonNumber())
				.setRepairDurationInHours(assumedWaggon.getRepairDurationInHours());
	}
}