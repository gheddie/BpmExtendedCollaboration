package de.gravitex.bpm.helper;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonDamageRepairAssumption;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonErrorCode;
import de.gravitex.bpm.helper.enumeration.traindepartmentnew.DepartureOrderState;
import de.gravitex.bpm.helper.logic.traindepartmentnew.TrainDepartureLogic;
import de.gravitex.bpm.helper.runner.TrainDepartmentNewRunner;
import de.gravitex.bpm.helper.util.WaggonList;

public class TrainDepartmentNewTestCase {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "trainDepartmentNew.bpmn" })
	public void testConcurringDepartureOrders() {

		new TrainDepartmentNewRunner(processEngine).startDepartureProcess(new WaggonList().withWaggonData("W0").getWaggonList());
		new TrainDepartmentNewRunner(processEngine).startDepartureProcess(new WaggonList().withWaggonData("W0").getWaggonList());

		// we have only 1 departure order...
		assertEquals(1, TrainDepartureLogic.getInstance().getDepartureOrders(DepartureOrderState.ACTIVE).size());

		// ...and only 1 process!!
		assertEquals(1, processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Trainpartment.TrainStation.DEF.DEF_TRAIN_STATION_PROCESS).list().size());
	}
	
	@Test
	@Deployment(resources = { "trainDepartmentNew.bpmn" })
	public void testRestartDepartingOrder() {
		
		TrainDepartmentNewRunner runner = new TrainDepartmentNewRunner(processEngine);

		ProcessInstance masterProcessInstance = runner.startDepartureProcess(
				new WaggonList().withWaggonData("W1@D1=C1,N1#D2=C2").withWaggonData("W2").withWaggonData("W3@D2=C1,C3,C4").getWaggonList());
		
		// we have 5 facility processes in master
		assertEquals(5,
				processEngine.getRuntimeService().createProcessInstanceQuery()
						.processDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.DEF.DEF_REPAIR_FACILITY_PROCESS)
						.variableValueEquals(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK, masterProcessInstance.getBusinessKey())
						.list().size());

		assertThat(masterProcessInstance).isWaitingAt(ProcessConstants.Trainpartment.RepairFacility.CATCH.CATCH_MSG_WAGGON_DAMAGE_ASSUMED);

		// assumements
		runner.assumeWaggonDamages(masterProcessInstance, WaggonDamageRepairAssumption.fromValues("W1", "D1", WaggonErrorCode.C1, 13),
				WaggonDamageRepairAssumption.fromValues("W1", "D2", WaggonErrorCode.C2, 27));

		runner.assumeWaggonDamages(masterProcessInstance, WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C1, 25),
				WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C3, 26),
				WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C4, 27));

		assertThat(masterProcessInstance).isWaitingAt(ProcessConstants.Trainpartment.TrainStation.TASK.TASK_PROCESS_ROLLOUT);
		
		runner.processRollout(masterProcessInstance, false);
	}

	@Test
	@Deployment(resources = { "trainDepartmentNew.bpmn" })
	public void testMultipleDepartures() {

		TrainDepartmentNewRunner runner1 = new TrainDepartmentNewRunner(processEngine);
		TrainDepartmentNewRunner runner2 = new TrainDepartmentNewRunner(processEngine);

		ProcessInstance masterProcessInstance1 = runner1.startDepartureProcess(
				new WaggonList().withWaggonData("W1@D1=C1,N1#D2=C2").withWaggonData("W2").withWaggonData("W3@D2=C1,C3,C4").getWaggonList());
		ProcessInstance masterProcessInstance2 = runner2.startDepartureProcess(
				new WaggonList().withWaggonData("W11@D1=C1,N1#D2=C2").withWaggonData("W12").withWaggonData("W13@D2=C1,C3,C4").getWaggonList());
		
		// 2 processes...
		assertEquals(2, processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Trainpartment.TrainStation.DEF.DEF_TRAIN_STATION_PROCESS).list().size());

		// we have 5 facility processes in master1
		assertEquals(5,
				processEngine.getRuntimeService().createProcessInstanceQuery()
						.processDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.DEF.DEF_REPAIR_FACILITY_PROCESS)
						.variableValueEquals(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK, masterProcessInstance1.getBusinessKey())
						.list().size());

		// we have 5 facility processes in master2
		assertEquals(5,
				processEngine.getRuntimeService().createProcessInstanceQuery()
						.processDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.DEF.DEF_REPAIR_FACILITY_PROCESS)
						.variableValueEquals(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK, masterProcessInstance2.getBusinessKey())
						.list().size());

		assertThat(masterProcessInstance1).isWaitingAt(ProcessConstants.Trainpartment.RepairFacility.CATCH.CATCH_MSG_WAGGON_DAMAGE_ASSUMED);
		assertThat(masterProcessInstance2).isWaitingAt(ProcessConstants.Trainpartment.RepairFacility.CATCH.CATCH_MSG_WAGGON_DAMAGE_ASSUMED);

		// assumements for master 1
		runner1.assumeWaggonDamages(masterProcessInstance1, WaggonDamageRepairAssumption.fromValues("W1", "D1", WaggonErrorCode.C1, 13),
				WaggonDamageRepairAssumption.fromValues("W1", "D2", WaggonErrorCode.C2, 27));

		runner1.assumeWaggonDamages(masterProcessInstance1, WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C1, 25),
				WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C3, 26),
				WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C4, 27));

		// assumements for master 2
		runner2.assumeWaggonDamages(masterProcessInstance2, WaggonDamageRepairAssumption.fromValues("W11", "D1", WaggonErrorCode.C1, 13),
				WaggonDamageRepairAssumption.fromValues("W11", "D2", WaggonErrorCode.C2, 27));

		runner2.assumeWaggonDamages(masterProcessInstance2, WaggonDamageRepairAssumption.fromValues("W13", "D2", WaggonErrorCode.C1, 25),
				WaggonDamageRepairAssumption.fromValues("W13", "D2", WaggonErrorCode.C3, 26),
				WaggonDamageRepairAssumption.fromValues("W13", "D2", WaggonErrorCode.C4, 27));

		assertThat(masterProcessInstance1).isWaitingAt(ProcessConstants.Trainpartment.TrainStation.TASK.TASK_PROCESS_ROLLOUT);
		assertThat(masterProcessInstance2).isWaitingAt(ProcessConstants.Trainpartment.TrainStation.TASK.TASK_PROCESS_ROLLOUT);
	}

	// ---

	@Before
	public void resetLogic() {
		TrainDepartureLogic.getInstance().reset();
	}
}