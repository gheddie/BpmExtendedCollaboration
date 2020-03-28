package de.gravitex.bpm.helper;

import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.base.BpmEngineTest;
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
		
		// all 'another slaves' must be gone, too...
		assertEquals(0, processEngine.getRuntimeService().createProcessInstanceQuery().list().size());
	}
}