package de.gravitex.bpm.helper.runner;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;
import lombok.Data;

@Data
public class CollaborationRunner {

	private ProcessEngineServices processEngine;

	private CollaborationRunner() {
		// ...
	}

	public void toS5(ProcessInstance masterProcessInstance, ProcessInstance slaveProcessInstance) {
		// slave should be at 'TASK_S5'...
		assertThat(slaveProcessInstance).isWaitingAt(ProcessConstants.Slave.TASK.TASK_S5);
		executeAndAssertSingleTask(processEngine, slaveProcessInstance,
				ProcessConstants.Slave.TASK.TASK_S5, null, true);
		// master finished...
		assertThat(masterProcessInstance).isEnded();
		// slave finished...
		assertThat(slaveProcessInstance).isEnded();
	}

	public void toM5(ProcessInstance masterProcessInstance, ProcessInstance slaveProcessInstance) {
		// slave
		assertThat(slaveProcessInstance).isWaitingAt(ProcessConstants.Slave.GATEWAY.GW_SLAVE_2);
		// master should wait on 'M5' as 'MSG_RECALL_M5' was invoked
		executeAndAssertSingleTask(processEngine, masterProcessInstance,
				ProcessConstants.Main.TASK.TASK_M5, null, true);
	}

	@SuppressWarnings("unchecked")
	public ProcessInstance toS1(ProcessInstance masterProcessInstance) {
		// we have a slave process
		List<ProcessInstance> slaveProcessInstanceList = processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Slave.DEF.DEF_SLAVE_PROCESS)
				.variableValueEquals(ProcessConstants.Slave.VAR.VAR_MASTER_PROCESS_BK,
						masterProcessInstance.getBusinessKey())
				.list();
		assertEquals(1, slaveProcessInstanceList.size());
		ProcessInstance slaveProcessInstance = slaveProcessInstanceList.get(0);
		// slave
		executeAndAssertSingleTask(processEngine, slaveProcessInstance,
				ProcessConstants.Slave.TASK.TASK_S1, HashMapBuilder.create()
						.withValuePair(ProcessConstants.Slave.VAR.VAR_SUBVAL, 4).build(),
				true);
		return slaveProcessInstance;
	}

	@SuppressWarnings("unchecked")
	public ProcessInstance toM2() {
		String mainBusinessKey = ProcessHelper
				.createBusinessKey(ProcessConstants.Main.DEF.DEF_MAIN_PROCESS);
		ProcessInstance masterProcessInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(ProcessConstants.Main.DEF.DEF_MAIN_PROCESS,
						mainBusinessKey, HashMapBuilder.create()
								.withValuePair(ProcessConstants.Main.VAR.VAR_MAINVAL, 2).build());
		// master
		executeAndAssertSingleTask(processEngine, masterProcessInstance,
				ProcessConstants.Main.TASK.TASK_M2, null, true);
		return masterProcessInstance;
	}

	// ---

	public static CollaborationRunner withProcessEngine(ProcessEngineServices processEngine) {
		CollaborationRunner collaborationRunner = new CollaborationRunner();
		collaborationRunner.setProcessEngine(processEngine);
		return collaborationRunner;
	}

	// ---

	private Task executeAndAssertSingleTask(ProcessEngineServices processEngine,
			ProcessInstance processInstance, String taskDefinitionKey,
			Map<String, Object> variables, boolean execute) {
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