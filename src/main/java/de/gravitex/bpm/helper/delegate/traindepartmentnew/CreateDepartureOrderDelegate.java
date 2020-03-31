package de.gravitex.bpm.helper.delegate.traindepartmentnew;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.delegate.base.ExtendedJavaDelegate;
import de.gravitex.bpm.helper.exception.traindepartmentnew.CreateDepartureOrderException;
import de.gravitex.bpm.helper.logic.traindepartmentnew.TrainDepartmentLogic;

public class CreateDepartureOrderDelegate extends ExtendedJavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		try {
			TrainDepartmentLogic.getInstance().createDepartureOrder(execution.getBusinessKey(),
					getTrainDepartmentData(execution).getWaggonList());
		} catch (CreateDepartureOrderException e) {
			throw new BpmnError(ProcessConstants.Trainpartment.TrainStation.ERROR.ERROR_CREATE_DO);
		}
	}
}