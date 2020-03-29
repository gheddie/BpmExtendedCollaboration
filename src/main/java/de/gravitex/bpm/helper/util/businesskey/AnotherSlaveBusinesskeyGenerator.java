package de.gravitex.bpm.helper.util.businesskey;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.ProcessDataItem;
import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;

public class AnotherSlaveBusinesskeyGenerator extends BusinesskeyGenerator {

	@Override
	protected List<Object> getBusinesskeyComponents() {
		List<Object> parent = super.getBusinesskeyComponents();
		ProcessDataItem item = (ProcessDataItem) getVariables().get(ProcessConstants.AnotherSlave.VAR.VAR_ANOTHER_SLAVE_ITEM);
		List<Object> values = new ArrayList<Object>();
		for (Object o : parent) {
			values.add(o);
		}
		values.add(item.getValue());
		return values;
	}
}