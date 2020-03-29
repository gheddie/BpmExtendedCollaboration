package de.gravitex.bpm.helper.logic.collaborationtest;

import lombok.Data;

@Data
public class ProcessDataItem {

	private String value;
	
	private ProcessDataItem() {
		// ...
	}

	public static ProcessDataItem fromString(String string) {
		ProcessDataItem processDataItem = new ProcessDataItem();
		processDataItem.setValue(string);
		return processDataItem;
	}
}