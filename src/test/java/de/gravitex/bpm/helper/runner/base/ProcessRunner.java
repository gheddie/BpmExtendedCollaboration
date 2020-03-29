package de.gravitex.bpm.helper.runner.base;

import java.util.Map;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

public abstract class ProcessRunner {

	protected Task executeAndAssertSingleTask(ProcessEngineServices processEngine, ProcessInstance processInstance,
			String taskDefinitionKey, Map<String, Object> variables, boolean execute) {
		TaskService taskService = processEngine.getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		if (processInstance != null) {
			taskQuery.processInstanceId(processInstance.getId());
		}
		Task task = taskQuery.taskDefinitionKey(taskDefinitionKey).singleResult();
		if (execute) {
			taskService.complete(task.getId(), variables);
		}
		return task;
	}
}