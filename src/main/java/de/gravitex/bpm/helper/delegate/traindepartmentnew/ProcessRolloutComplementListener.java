package de.gravitex.bpm.helper.delegate.traindepartmentnew;

import org.camunda.bpm.engine.delegate.DelegateTask;

import de.gravitex.bpm.helper.enumeration.traindepartmentnew.DepartureOrderState;
import de.gravitex.bpm.helper.listener.base.ExtendedTaskListener;

public class ProcessRolloutComplementListener extends ExtendedTaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		updateDepartingOrder(delegateTask.getExecution(), DepartureOrderState.CANCELLED);
	}
}