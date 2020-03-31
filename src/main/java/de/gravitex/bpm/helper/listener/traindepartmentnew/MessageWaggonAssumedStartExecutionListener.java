package de.gravitex.bpm.helper.listener.traindepartmentnew;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonDamageRepairAssumption;
import de.gravitex.bpm.helper.listener.base.ExtendedExecutionListener;

public class MessageWaggonAssumedStartExecutionListener extends ExtendedExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		WaggonDamageRepairAssumption waggonDamageRepairAssumption = (WaggonDamageRepairAssumption) execution.getVariable(ProcessConstants.Trainpartment.RepairFacility.VAR.VAR_WAGGON_ASSUMPTION_RESULT);
		// update repair time for waggon in process data
		getTrainDepartmentData(execution).updateRepairAssumement(waggonDamageRepairAssumption);
	}
}