package de.gravitex.bpm.helper.listener.traindepartmentnew;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.listener.base.ExtendedExecutionListener;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;

public class MessageWaggonAssumedStartExecutionListener extends ExtendedExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		Waggon assumedWaggon = (Waggon) execution.getVariable(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_SINGLE_WAGGON_TO_ASSUME);
		int werner = 5;
	}
}