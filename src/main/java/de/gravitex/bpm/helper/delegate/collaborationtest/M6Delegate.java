package de.gravitex.bpm.helper.delegate.collaborationtest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.collaborationtest.ProcessDataItem;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class M6Delegate implements JavaDelegate {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ProcessDataItem item = (ProcessDataItem) execution.getVariable(ProcessConstants.Main.VAR.VAR_PROCESS_DATA_ITEM);
		ProcessHelper.startProcessInstanceByMessage(execution.getProcessEngine(),
				ProcessConstants.AnotherSlave.DEF.DEF_ANOTHER_SLAVE_PROCESS,
				ProcessConstants.AnotherSlave.MSG.MSG_START_ANOTHER_SLAVE,
				HashMapBuilder.create()
						.withValuePair(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK, execution.getBusinessKey()).withValuePair(ProcessConstants.AnotherSlave.VAR.VAR_ANOTHER_SLAVE_ITEM, item)
						.build(), execution.getBusinessKey());
	}
}