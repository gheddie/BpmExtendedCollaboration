package de.gravitex.bpm.helper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.base.BpmEngineTest;
import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.runner.CollaborationRunner;

public class CollaborationTest extends BpmEngineTest {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "collaborationTest.bpmn" })
	public void testM2_S1_S4_M5_S5() {

		CollaborationRunner runner1 = CollaborationRunner.withProcessEngine(processEngine);
		CollaborationRunner runner2 = CollaborationRunner.withProcessEngine(processEngine);

		ProcessInstance masterProcessInstance1 = runner1.toM2();
		ProcessInstance masterProcessInstance2 = runner2.toM2();

		ProcessInstance slaveProcessInstance1 = runner1.toS1(masterProcessInstance1);
		ProcessInstance slaveProcessInstance2 = runner2.toS1(masterProcessInstance2);

		runner1.toM5(masterProcessInstance1, slaveProcessInstance1);
		runner2.toM5(masterProcessInstance2, slaveProcessInstance2);

		runner1.toS5(masterProcessInstance1, slaveProcessInstance1);
		runner2.toS5(masterProcessInstance2, slaveProcessInstance2);

		// all slaves gone...
		assertEquals(0, processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Slave.DEF.DEF_SLAVE_PROCESS).list().size());
		
		// 2 mains left...
		assertEquals(2, processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Main.DEF.DEF_MAIN_PROCESS).list().size());

		// 2 'another slaves' with parent 'masterProcessInstance1'...
		assertEquals(2, getAnotherSlavesForMaster(masterProcessInstance1).size());

		// 2 'another slaves' with parent 'masterProcessInstance2'...
		assertEquals(2, getAnotherSlavesForMaster(masterProcessInstance2).size());
	}

	private List<ProcessInstance> getAnotherSlavesForMaster(ProcessInstance masterProcessInstance) {
		
		return processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.AnotherSlave.DEF.DEF_ANOTHER_SLAVE_PROCESS)
				.variableValueEquals(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK,
						masterProcessInstance.getBusinessKey())
				.list();
	}
}