package de.gravitex.bpm.helper.listener.collaborationtest;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class M2CompletementListener implements TaskListener {

	@SuppressWarnings("unchecked")
	@Override
	public void notify(DelegateTask delegateTask) {
		ProcessHelper.startProcessInstanceByMessage(delegateTask.getExecution().getProcessEngineServices(),
				ProcessConstants.Collaboration.Slave.DEF.DEF_SLAVE_PROCESS, ProcessConstants.Collaboration.Slave.MSG.MSG_CALL_A,
				HashMapBuilder.create().withValuePair(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK,
						delegateTask.getExecution().getBusinessKey()).build(), delegateTask.getExecution().getBusinessKey());
	}
}