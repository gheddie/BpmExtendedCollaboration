package de.gravitex.bpm.helper;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonDamageRepairAssumption;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonErrorCode;
import de.gravitex.bpm.helper.runner.TrainDepartmentNewRunner;
import de.gravitex.bpm.helper.util.WaggonList;

public class TrainDepartmentNewTestCase {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "trainDepartmentNew.bpmn" })
	public void testSimpleDeparture() {

		TrainDepartmentNewRunner runner1 = new TrainDepartmentNewRunner(processEngine);
		TrainDepartmentNewRunner runner2 = new TrainDepartmentNewRunner(processEngine);

		ProcessInstance masterProcessInstance1 = runner1.startDepartureProcess(
				new WaggonList().withWaggonData("W1@D1=C1,N1#D2=C2").withWaggonData("W2").withWaggonData("W3@D2=C1,C3,C4").getWaggonList());
		ProcessInstance masterProcessInstance2 = runner1.startDepartureProcess(
				new WaggonList().withWaggonData("W1@D1=C1,N1#D2=C2").withWaggonData("W2").withWaggonData("W3@D2=C1,C3,C4").getWaggonList());

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
		
		runner2.assumeWaggonDamages(masterProcessInstance2, WaggonDamageRepairAssumption.fromValues("W1", "D1", WaggonErrorCode.C1, 13),
				WaggonDamageRepairAssumption.fromValues("W1", "D2", WaggonErrorCode.C2, 27));

		runner2.assumeWaggonDamages(masterProcessInstance2, WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C1, 25),
				WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C3, 26),
				WaggonDamageRepairAssumption.fromValues("W3", "D2", WaggonErrorCode.C4, 27));
		
		assertThat(masterProcessInstance1).isEnded();
		assertThat(masterProcessInstance2).isEnded();
	}
}