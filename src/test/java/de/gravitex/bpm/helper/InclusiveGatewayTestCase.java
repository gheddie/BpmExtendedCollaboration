package de.gravitex.bpm.helper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.collaborationtest.ProcessObject;

public class InclusiveGatewayTestCase {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@SuppressWarnings("unchecked")
	@Test
	@Deployment(resources = { "inclusiveGatewayTest.bpmn" })
	public void testInclusiveGateway() {
		processEngine.getRuntimeService().startProcessInstanceByKey("DEF_INCL_TEST", "123",
				HashMapBuilder.create().withValuePair("VAR_PROCESS_OBJ", new ProcessObject()).build());
		
		TaskService taskService = processEngine.getTaskService();
		
		List<Task> tasksT1 = taskService.createTaskQuery().taskDefinitionKey("TASK_T1").list();
		assertEquals(1, tasksT1.size());
		
		List<Task> tasksT2 = taskService.createTaskQuery().taskDefinitionKey("TASK_T2").list();
		assertEquals(1, tasksT2.size());
		
		List<Task> tasksT3 = taskService.createTaskQuery().taskDefinitionKey("TASK_T3").list();
		assertEquals(0, tasksT3.size());
		
		// ---
		
		// --> no 'T4'
		assertEquals(0, taskService.createTaskQuery().taskDefinitionKey("TASK_T4").list().size());
		
		// complete 'T1'...
		taskService.complete(tasksT1.get(0).getId());
		
		// --> no 'T4'
		assertEquals(0, taskService.createTaskQuery().taskDefinitionKey("TASK_T4").list().size());
		
		// complete 'T2'...
		taskService.complete(tasksT2.get(0).getId());
		
		// --> 'T4' there !!!!!!!!!!!!!!!!!!
		assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("TASK_T4").list().size());
	}
}