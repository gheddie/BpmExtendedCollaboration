package de.gravitex.bpm.helper.entity.collaborationtest;

import de.gravitex.bpm.helper.util.businesskey.base.ProcessIdentifier;
import lombok.Data;

@Data
public class ProcessDataItem implements ProcessIdentifier {

	private String value;
	
	private ProcessDataItem() {
		// ...
	}

	public static ProcessDataItem fromString(String string) {
		ProcessDataItem processDataItem = new ProcessDataItem();
		processDataItem.setValue(string);
		return processDataItem;
	}

	@Override
	public String generateProcessIdentifier() {
		return value;
	}
}