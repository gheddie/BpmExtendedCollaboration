package de.gravitex.bpm.helper.delegate.traindepartmentnew;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonDamageInfo;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class OrderWaggonRepairsDelegate implements JavaDelegate {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Waggon waggon = (Waggon) execution.getVariable(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_SINGLE_WAGGON_DAMAGE_TO_ASSUME);
		for (WaggonDamageInfo criticalWaggonDamageInfo : waggon.getCritialDamages()) {
			ProcessHelper.startProcessInstanceByMessage(execution.getProcessEngine(),
					ProcessConstants.Trainpartment.RepairFacility.DEF.DEF_REPAIR_FACILITY_PROCESS,
					ProcessConstants.Trainpartment.RepairFacility.MSG.MSG_START_ASSUMPTION,
					HashMapBuilder.create()
							.withValuePair(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_SINGLE_WAGGON_DAMAGE_TO_ASSUME,
									criticalWaggonDamageInfo)
							.build(),
					execution.getBusinessKey());
		}
	}
}