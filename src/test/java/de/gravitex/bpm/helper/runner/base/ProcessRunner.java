package de.gravitex.bpm.helper.runner.base;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import lombok.Data;

@Data
public abstract class ProcessRunner<T> {
	
	private ProcessEngineServices processEngine;
	
	// the started process instance
	private ProcessInstance processInstance;

	private T processInputData;
	
	public ProcessRunner(ProcessEngineServices aProcessEngine) {
		super();
		this.processEngine = aProcessEngine;
	}
	
	protected abstract ProcessInstance startProcessInstance(T processInputData);
	
	public void startProcess(T aProcessInputData) {
		this.processInputData = aProcessInputData;
		processInstance = startProcessInstance(processInputData);
	}

	public Task executeAndAssertSingleTask(ProcessEngineServices processEngine, ProcessInstance processInstance,
			String taskDefinitionKey, Map<String, Object> variables, boolean execute) {
		TaskService taskService = processEngine.getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		if (processInstance != null) {
			taskQuery.processInstanceId(processInstance.getId());
		}
		Task task = taskQuery.taskDefinitionKey(taskDefinitionKey).singleResult();
		assertNotNull(task);
		if (execute) {
			taskService.complete(task.getId(), variables);
		}
		return task;
	}
	
	protected abstract String getProcessDefinitionKey();
}