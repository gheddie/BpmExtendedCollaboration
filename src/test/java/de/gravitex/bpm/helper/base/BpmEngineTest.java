package de.gravitex.bpm.helper.base;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

public abstract class BpmEngineTest {

	protected void executeAndAssertSingleTask(ProcessEngineServices processEngine, ProcessInstance processInstance,
			String taskDefinitionKey, Map<String, Object> variables, boolean execute) {
		TaskService taskService = processEngine.getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> taskList = taskQuery.taskDefinitionKey(taskDefinitionKey).list();
		if (processInstance != null) {
			taskQuery.processInstanceId(processInstance.getId());
		}
		assertEquals(1, taskList.size());
		if (execute) {
			taskService.complete(taskList.get(0).getId(), variables);			
		}
	}
}