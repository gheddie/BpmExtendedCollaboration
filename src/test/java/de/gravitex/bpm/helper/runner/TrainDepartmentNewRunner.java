package de.gravitex.bpm.helper.runner;

import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.traindepartmentnew.TrainDepartmentData;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.runner.base.ProcessRunner;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class TrainDepartmentNewRunner extends ProcessRunner {

	public TrainDepartmentNewRunner(ProcessEngineServices aProcessEngine) {
		super(aProcessEngine);
	}

	@SuppressWarnings("unchecked")
	public void assumeWaggons() {
		ProcessInstance processInstance = ProcessHelper.startProcessInstanceByMessage(getProcessEngine(),
				ProcessConstants.Trainpartment.TrainStation.DEF.DEF_TRAIN_STATION_PROCESS,
				ProcessConstants.Trainpartment.TrainStation.MSG.MSG_DEPARTURE_ORDERED,
				HashMapBuilder.create()
						.withValuePair(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_TRAIN_DEPARTMENT_DATA, new TrainDepartmentData()
								.addWaggons(Waggon.fromWaggonData("W1@C1,N1"), Waggon.fromWaggonData("W2"), Waggon.fromWaggonData("W3@C1")))
						.build(),
				null);
		assertEquals(2, getProcessEngine().getTaskService().createTaskQuery()
				.taskDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.TASK.TASK_ASSUME_WAGGON).list().size());
	}
}