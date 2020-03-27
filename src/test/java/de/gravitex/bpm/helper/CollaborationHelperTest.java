package de.gravitex.bpm.helper;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.base.BpmEngineTest;
import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class CollaborationHelperTest extends BpmEngineTest {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@SuppressWarnings("unchecked")
	@Test
	@Deployment(resources = { "collaborationTest.bpmn" })
	public void testM2_S1_S4_M5_S5() {

		String mainBusinessKey = ProcessHelper.createBusinessKey(ProcessConstants.Main.DEF.DEF_MAIN_PROCESS);
		ProcessInstance masterProcessInstance = processEngine.getRuntimeService().startProcessInstanceByKey(
				ProcessConstants.Main.DEF.DEF_MAIN_PROCESS, mainBusinessKey,
				HashMapBuilder.create().withValuePair(ProcessConstants.Main.VAR.VAR_MAINVAL, 2).build());

		// master
		executeAndAssertSingleTask(processEngine, masterProcessInstance, ProcessConstants.Main.TASK.TASK_M2, null, true);
		
		// we have a slave process
		List<ProcessInstance> slaveProcessInstanceList = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Slave.DEF.DEF_SLAVE_PROCESS).list();
		assertEquals(1, slaveProcessInstanceList.size());
		
		ProcessInstance slaveProcessInstance = slaveProcessInstanceList.get(0);

		// slave
		executeAndAssertSingleTask(processEngine, slaveProcessInstance, ProcessConstants.Slave.TASK.TASK_S1,
				HashMapBuilder.create().withValuePair(ProcessConstants.Slave.VAR.VAR_SUBVAL, 4).build(), true);
		
		// slave
		assertThat(slaveProcessInstance).isWaitingAt(ProcessConstants.Slave.GATEWAY.GW_SLAVE_2);
		
		// master should wait on 'M5' as 'MSG_RECALL_M5' was invoked
		executeAndAssertSingleTask(processEngine, masterProcessInstance, ProcessConstants.Main.TASK.TASK_M5, null,
				true);
		
		// slave should be at 'TASK_S5'...
		assertThat(slaveProcessInstance).isWaitingAt(ProcessConstants.Slave.TASK.TASK_S5);
	}
}