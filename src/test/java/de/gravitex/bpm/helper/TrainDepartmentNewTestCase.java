package de.gravitex.bpm.helper;

import static org.junit.Assert.assertEquals;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonRepairAssumption;
import de.gravitex.bpm.helper.runner.TrainDepartmentNewRunner;

public class TrainDepartmentNewTestCase {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "trainDepartmentNew.bpmn" })
	public void testSimpleDeparture() {

		TrainDepartmentNewRunner runner = new TrainDepartmentNewRunner(processEngine);

		List<Waggon> waggonList = new ArrayList<Waggon>();
		waggonList.add(Waggon.fromWaggonData("W1@D1=C1,N1#D2=C2"));
		waggonList.add(Waggon.fromWaggonData("W2"));
		waggonList.add(Waggon.fromWaggonData("W3@D2=C1"));
		
		ProcessInstance masterProcessInstance = runner.startDepartureProcess(waggonList);
		
		assertEquals(2, processEngine.getTaskService().createTaskQuery()
				.taskDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.TASK.TASK_ASSUME_WAGGON).list().size());

		runner.assumeWaggons(masterProcessInstance, WaggonRepairAssumption.fromValues("W1", 13));
		runner.assumeWaggons(masterProcessInstance, WaggonRepairAssumption.fromValues("W3", 25));
		
		assertThat(masterProcessInstance).isEnded();
	}
}