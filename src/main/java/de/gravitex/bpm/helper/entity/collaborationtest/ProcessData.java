package de.gravitex.bpm.helper.entity.collaborationtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class ProcessData {
	
	private HashMap<String, ProcessDataItem> processDataItems = new HashMap<String, ProcessDataItem>();

	private ProcessData() {
		// ...
	}

	public static ProcessData fromStrings(String[] strings) {
		ProcessData processData = new ProcessData();
		for (String string : strings) {
			processData.addItem(ProcessDataItem.fromString(string));
		}
		return processData;
	}

	private void addItem(ProcessDataItem processDataItem) {
		processDataItems.put(processDataItem.getValue(), processDataItem);
	}
	
	public List<ProcessDataItem> getItems() {
		List<ProcessDataItem> items = new ArrayList<ProcessDataItem>();
		for (ProcessDataItem processDataItem : processDataItems.values()) {
			items.add(processDataItem);
		}
		return items;
	}
}