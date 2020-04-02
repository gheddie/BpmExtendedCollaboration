package de.gravitex.bpm.helper.runner.collaborationtest;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.entity.collaborationtest.ProcessData;
import de.gravitex.bpm.helper.runner.TrainDepartmentNewRunner;
import de.gravitex.bpm.helper.runner.base.ProcessRunner;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;
import lombok.Data;

@Data
public class CollaborationRunner extends ProcessRunner<Object> {
	
	public CollaborationRunner(ProcessEngineServices processEngine) {
		super(processEngine);
	}

	public void toS5(ProcessInstance masterProcessInstance, ProcessInstance slaveProcessInstance) {
		// slave should be at 'TASK_S5'...
		assertThat(slaveProcessInstance).isWaitingAt(ProcessConstants.Collaboration.Slave.TASK.TASK_S5);
		executeAndAssertSingleTask(getProcessEngine(), slaveProcessInstance, ProcessConstants.Collaboration.Slave.TASK.TASK_S5, null,
				true);
		// slave finished...
		assertThat(slaveProcessInstance).isEnded();
	}

	public void toM5(ProcessInstance masterProcessInstance, ProcessInstance slaveProcessInstance) {
		// slave
		assertThat(slaveProcessInstance).isWaitingAt(ProcessConstants.Collaboration.Slave.GATEWAY.GW_SLAVE_2);
		// master should wait on 'M5' as 'MSG_RECALL_M5' was invoked
		executeAndAssertSingleTask(getProcessEngine(), masterProcessInstance, ProcessConstants.Collaboration.Main.TASK.TASK_M5, null,
				true);
	}
	
	public void toEnd(ProcessInstance masterProcessInstance) {
		List<Task> m7Tasks = getProcessEngine().getTaskService().createTaskQuery().processInstanceId(masterProcessInstance.getId())
				.taskDefinitionKey(ProcessConstants.Collaboration.Main.TASK.TASK_M7).list();
		assertEquals(2,
				m7Tasks.size());
		for (Task task : m7Tasks) {
			getProcessEngine().getTaskService().complete(task.getId());
		}
	}

	@SuppressWarnings("unchecked")
	public ProcessInstance toS1(ProcessInstance masterProcessInstance) {
		// we have a slave process
		List<ProcessInstance> slaveProcessInstanceList = getProcessEngine().getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey(ProcessConstants.Collaboration.Slave.DEF.DEF_SLAVE_PROCESS)
				.variableValueEquals(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK,
						masterProcessInstance.getBusinessKey())
				.list();
		assertEquals(1, slaveProcessInstanceList.size());
		ProcessInstance slaveProcessInstance = slaveProcessInstanceList.get(0);
		// slave
		executeAndAssertSingleTask(getProcessEngine(), slaveProcessInstance, ProcessConstants.Collaboration.Slave.TASK.TASK_S1,
				HashMapBuilder.create().withValuePair(ProcessConstants.Collaboration.Slave.VAR.VAR_SUBVAL, "S4").build(), true);
		return slaveProcessInstance;
	}

	@SuppressWarnings("unchecked")
	public ProcessInstance toM2() {
		ProcessInstance masterProcessInstance = ProcessHelper.startProcessInstanceByKey(getProcessEngine(),
				ProcessConstants.Collaboration.Main.DEF.DEF_MAIN_PROCESS,
				HashMapBuilder.create().withValuePair(ProcessConstants.Collaboration.Main.VAR.VAR_MAINVAL, "M2")
						.withValuePair(ProcessConstants.Collaboration.Main.VAR.VAR_PROCESS_DATA,
								ProcessData.fromStrings(new String[] { "A", "B" }))
						.build(), null);
		// master
		executeAndAssertSingleTask(getProcessEngine(), masterProcessInstance, ProcessConstants.Collaboration.Main.TASK.TASK_M2, null,
				true);
		return masterProcessInstance;
	}

	@Override
	public ProcessRunner<Object> startProcessInstance(Object processInputData) {
		// TODO Auto-generated method stub
		return null;
	}
}