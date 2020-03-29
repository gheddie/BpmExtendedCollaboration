package de.gravitex.bpm.helper;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.traindepartmentnew.TrainDepartmentData;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class TrainDepartmentNewTestCase {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@SuppressWarnings("unchecked")
	@Test
	@Deployment(resources = { "trainDepartmentNew.bpmn" })
	public void testSimpleDeparture() {

		ProcessInstance processInstance = ProcessHelper.startProcessInstanceByMessage(processEngine,
				ProcessConstants.Trainpartment.Main.DEF.DEF_DEPARTMENT_MAIN_PROCESS, ProcessConstants.Trainpartment.Main.MSG.MSG_DEPARTURE_ORDERED,
				HashMapBuilder.create()
						.withValuePair(ProcessConstants.Trainpartment.Main.VAR.VAR_TRAIN_DEPARTMENT_DATA, new TrainDepartmentData()
								.addWaggons(Waggon.fromWaggonData("W1@C1,N1"), Waggon.fromWaggonData("W2"), Waggon.fromWaggonData("W3@C1")))
						.build(),
				null);
	}
}