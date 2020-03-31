package de.gravitex.bpm.helper.util.businesskey.collaboration;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.collaborationtest.ProcessDataItem;
import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;

public class AnotherSlaveBusinesskeyGenerator extends BusinesskeyGenerator<ProcessDataItem> {

	@Override
	protected ProcessDataItem getAdditionalValueObject() {
		return (ProcessDataItem) getVariables().get(ProcessConstants.Collaboration.AnotherSlave.VAR.VAR_ANOTHER_SLAVE_ITEM);
	}
}