package de.gravitex.bpm.helper.listener.traindepartmentnew;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.listener.base.ExtendedTaskListener;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.util.HashMapBuilder;

public class AssumeWaggonTaskComplementListener extends ExtendedTaskListener {

	private static final Logger logger = Logger.getLogger(AssumeWaggonTaskComplementListener.class);

	@SuppressWarnings("unchecked")
	@Override
	public void notify(DelegateTask delegateTask) {
		RuntimeService runtimeService = delegateTask.getProcessEngine().getRuntimeService();
		Waggon assumedWaggon = (Waggon) runtimeService.getVariable(delegateTask.getExecution().getId(),
				ProcessConstants.Trainpartment.TrainStation.VAR.VAR_SINGLE_WAGGON_TO_ASSUME);
		runtimeService.correlateMessage(ProcessConstants.Trainpartment.TrainStation.MSG.MSG_WAGGON_ASSUMED,
				getMasterProcessBusinessKey(delegateTask.getExecution()), HashMapBuilder.create()
						.withValuePair(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_SINGLE_WAGGON_TO_ASSUME, assumedWaggon).build());
		int assumedRepairHours = (int) runtimeService.getVariable(delegateTask.getExecution().getId(),
				ProcessConstants.Trainpartment.RepairFacility.VAR.VAR_WAGGON_ASSUMPTION_RESULT);
		logger.info("assumed waggon: " + assumedWaggon.getWaggonNumber() + " --> " + assumedRepairHours + " hours.");
		logger.debug("successfully correlated message: " + ProcessConstants.Trainpartment.TrainStation.MSG.MSG_WAGGON_ASSUMED);
	}
}