package de.gravitex.bpm.helper.delegate.traindepartmentnew;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.delegate.base.ExtendedJavaDelegate;
import de.gravitex.bpm.helper.logic.traindepartmentnew.TrainDepartureLogic;

public class CreateDepartureOrderDelegate extends ExtendedJavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		TrainDepartureLogic.getInstance().createDepartureOrder(execution.getBusinessKey(),
				getTrainDepartmentData(execution).getWaggonList());
		getTrainDepartmentData(execution).setBusinessKey(execution.getBusinessKey());
	}
}