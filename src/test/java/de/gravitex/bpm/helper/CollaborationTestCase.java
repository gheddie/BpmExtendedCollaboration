package de.gravitex.bpm.helper;

import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.runner.collaborationtest.CollaborationRunner;

public class CollaborationTestCase {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "collaborationTest.bpmn" })
	public void testM2_S1_S4_M5_S5() {

		CollaborationRunner runner1 = new CollaborationRunner(processEngine);
		CollaborationRunner runner2 = new CollaborationRunner(processEngine);

		ProcessInstance masterProcessInstance1 = runner1.toM2();
		ProcessInstance masterProcessInstance2 = runner2.toM2();

		ProcessInstance slaveProcessInstance1 = runner1.toS1(masterProcessInstance1);
		ProcessInstance slaveProcessInstance2 = runner2.toS1(masterProcessInstance2);

		runner1.toM5(masterProcessInstance1, slaveProcessInstance1);
		runner2.toM5(masterProcessInstance2, slaveProcessInstance2);

		runner1.toS5(masterProcessInstance1, slaveProcessInstance1);
		runner2.toS5(masterProcessInstance2, slaveProcessInstance2);
		
		runner1.toEnd(masterProcessInstance1);
		runner2.toEnd(masterProcessInstance2);
		
		// all slaves gone...
		assertEquals(0, processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Collaboration.Slave.DEF.DEF_SLAVE_PROCESS).list().size());
		
		// all mains gone...
		assertEquals(0, processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(runner1.getProcessDefinitionKey()).list().size());
		
		// all another slaves gone...
		assertEquals(0, processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Collaboration.AnotherSlave.DEF.DEF_ANOTHER_SLAVE_PROCESS).list().size());
	}
}