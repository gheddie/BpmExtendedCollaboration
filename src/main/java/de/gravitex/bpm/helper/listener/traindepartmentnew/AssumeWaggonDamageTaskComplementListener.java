package de.gravitex.bpm.helper.listener.traindepartmentnew;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.listener.base.ExtendedTaskListener;
import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonDamageRepairAssumption;
import de.gravitex.bpm.helper.util.HashMapBuilder;

public class AssumeWaggonDamageTaskComplementListener extends ExtendedTaskListener {

	private static final Logger logger = Logger.getLogger(AssumeWaggonDamageTaskComplementListener.class);

	@SuppressWarnings("unchecked")
	@Override
	public void notify(DelegateTask delegateTask) {
		RuntimeService runtimeService = delegateTask.getProcessEngine().getRuntimeService();
		WaggonDamageRepairAssumption waggonDamageRepairAssumption = (WaggonDamageRepairAssumption) runtimeService.getVariable(delegateTask.getExecution().getId(),
				ProcessConstants.Trainpartment.RepairFacility.VAR.VAR_WAGGON_ASSUMPTION_RESULT);
		runtimeService.correlateMessage(ProcessConstants.Trainpartment.TrainStation.MSG.MSG_WAGGON_DAMAGE_ASSUMED,
				getMasterProcessBusinessKey(delegateTask.getExecution()), HashMapBuilder.create()
						.withValuePair(ProcessConstants.Trainpartment.RepairFacility.VAR.VAR_WAGGON_ASSUMPTION_RESULT, waggonDamageRepairAssumption).build());
		// logger.info("assumed waggon: " + waggonToAssume.getWaggonNumber() + " --> " + waggonDamageRepairAssumption + " hours.");
		logger.debug("successfully correlated message: " + ProcessConstants.Trainpartment.TrainStation.MSG.MSG_WAGGON_DAMAGE_ASSUMED);
	}
}