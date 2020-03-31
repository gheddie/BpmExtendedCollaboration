package de.gravitex.bpm.helper;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonDamageRepairAssumption;
import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonErrorCode;
import de.gravitex.bpm.helper.runner.TrainDepartmentNewRunner;
import de.gravitex.bpm.helper.util.WaggonList;

public class TrainDepartmentNewTestCase {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "trainDepartmentNew.bpmn" })
	public void testSimpleDeparture() {

		TrainDepartmentNewRunner runner = new TrainDepartmentNewRunner(processEngine);

		ProcessInstance masterProcessInstance = runner.startDepartureProcess(
				new WaggonList().withWaggonData("W1@D1=C1,N1#D2=C2").withWaggonData("W2").withWaggonData("W3@D2=C1").getWaggonList());

		assertEquals(2, processEngine.getTaskService().createTaskQuery()
				.taskDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.TASK.TASK_ASSUME_WAGGON).list().size());

		// we have 3 facility processes
		assertEquals(3,
				processEngine.getRuntimeService().createProcessInstanceQuery()
						.processDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.DEF.DEF_REPAIR_FACILITY_PROCESS)
						.variableValueEquals(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK, masterProcessInstance.getBusinessKey())
						.list().size());

		assertThat(masterProcessInstance).isWaitingAt(ProcessConstants.Trainpartment.RepairFacility.CATCH.CATCH_MSG_WAGGON_DAMAGE_ASSUMED);

		runner.assumeWaggonDamages(masterProcessInstance, WaggonDamageRepairAssumption.fromValues("W1", "D1", WaggonErrorCode.C1, 13));
		runner.assumeWaggonDamages(masterProcessInstance, WaggonDamageRepairAssumption.fromValues("W1", "D2", WaggonErrorCode.C2, 27));
		runner.assumeWaggonDamages(masterProcessInstance, WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C1, 25));

		assertThat(masterProcessInstance).isEnded();
	}
}