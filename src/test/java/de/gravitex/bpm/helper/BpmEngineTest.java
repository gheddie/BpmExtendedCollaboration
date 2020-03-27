package de.gravitex.bpm.helper;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

public abstract class BpmEngineTest {

	protected void executeAndAssertSingleTask(ProcessEngineServices processEngine, ProcessInstance processInstance,
			String taskDefinitionKey, Map<String, Object> variables) {
		TaskService taskService = processEngine.getTaskService();
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(taskDefinitionKey).list();
		assertEquals(1, taskList.size());
		taskService.complete(taskList.get(0).getId(), variables);
	}
}