package de.gravitex.bpm.helper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.util.HashMapBuilder;

public class CollaborationHelperTest extends BpmEngineTest {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@SuppressWarnings("unchecked")
	@Test
	@Deployment(resources = { "collaborationTest.bpmn" })
	public void testSingleInvocation() {

		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(
				ProcessConstants.Main.DEF.DEF_MAIN_PROCESS,
				HashMapBuilder.create().withValuePair(ProcessConstants.Main.VAR.VAR_MAINVAL, 1).build());

		executeAndAssertSingleTask(processEngine, processInstance, ProcessConstants.Main.TASK.TASK_M0, null);
	}
}