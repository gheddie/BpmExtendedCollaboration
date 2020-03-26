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

public class CollaborationHelperTest {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "collaborationTest.bpmn" })
	public void testSingleInvocation() {

		TaskService taskService = processEngine.getTaskService();

		ProcessInstance processInstance = runUpToS3();

		// 1 task 'S3' tasks in slave process...
		List<Task> tasksS3 = taskService.createTaskQuery().taskDefinitionKey(ProcessConstants.Slave.TASK.TASK_S3)
				.list();
		assertEquals(1, tasksS3.size());

		taskService.complete(tasksS3.get(0).getId());

		// 1 task 'M4'...
		assertEquals(1, taskService.createTaskQuery().processInstanceId(processInstance.getId())
				.taskDefinitionKey(ProcessConstants.Main.TASK.TASK_M4).list().size());
	}

	@Test
	@Deployment(resources = { "collaborationTest.bpmn" })
	public void testMultipleInvocation() {
		
		TaskService taskService = processEngine.getTaskService();

		ProcessInstance processInstanceA = runUpToS3();

		ProcessInstance processInstanceB = runUpToS3();
		
		// 2 task 'S3' tasks in slave processes...
		List<Task> tasksS3 = taskService.createTaskQuery().taskDefinitionKey(ProcessConstants.Slave.TASK.TASK_S3)
				.list();
		assertEquals(2, tasksS3.size());

		// execute both callbacks
		taskService.complete(tasksS3.get(0).getId());
		taskService.complete(tasksS3.get(1).getId());
		
		// 1 task 'M4' in 'A'...
		assertEquals(1, taskService.createTaskQuery().processInstanceId(processInstanceA.getId())
				.taskDefinitionKey(ProcessConstants.Main.TASK.TASK_M4).list().size());
		
		// 1 task 'M4' in 'B'...
		assertEquals(1, taskService.createTaskQuery().processInstanceId(processInstanceB.getId())
				.taskDefinitionKey(ProcessConstants.Main.TASK.TASK_M4).list().size());
	}

	private ProcessInstance runUpToS3() {

		ProcessInstance processInstance = ProcessRegistry.getInstance().startProcessInstanceByKey(processEngine,
				ProcessConstants.Main.DEF.MAIN_PROCESS, null);

		TaskService taskService = processEngine.getTaskService();

		List<Task> tasksM1 = taskService.createTaskQuery().taskDefinitionKey(ProcessConstants.Main.TASK.TASK_M1)
				.processInstanceId(processInstance.getId()).list();
		assertEquals(1, tasksM1.size());

		taskService.complete(tasksM1.get(0).getId());

		List<Task> tasksM2 = taskService.createTaskQuery().taskDefinitionKey(ProcessConstants.Main.TASK.TASK_M2)
				.processInstanceId(processInstance.getId()).list();
		assertEquals(1, tasksM2.size());

		// execute calls
		taskService.complete(tasksM2.get(0).getId());

		// 1 task 'S1' tasks in slave process...
		List<Task> tasksS1 = processEngine.getTaskService().createTaskQuery()
				.taskDefinitionKey(ProcessConstants.Slave.TASK.TASK_S1).list();
		assertEquals(1, tasksS1.size());

		taskService.complete(tasksS1.get(0).getId());

		return processInstance;
	}
}