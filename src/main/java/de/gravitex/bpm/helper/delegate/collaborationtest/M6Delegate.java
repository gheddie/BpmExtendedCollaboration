package de.gravitex.bpm.helper.delegate.collaborationtest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.entity.collaborationtest.ProcessDataItem;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class M6Delegate implements JavaDelegate {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ProcessDataItem item = (ProcessDataItem) execution
				.getVariable(ProcessConstants.Collaboration.Main.VAR.VAR_PROCESS_DATA_ITEM);
		ProcessHelper.startProcessInstanceByMessage(execution.getProcessEngine(),
				ProcessConstants.Collaboration.AnotherSlave.DEF.DEF_ANOTHER_SLAVE_PROCESS,
				ProcessConstants.Collaboration.AnotherSlave.MSG.MSG_START_ANOTHER_SLAVE,
				HashMapBuilder.create()
						.withValuePair(ProcessConstants.Collaboration.AnotherSlave.VAR.VAR_ANOTHER_SLAVE_ITEM, item)
						.build(),
				execution.getBusinessKey());
	}
}