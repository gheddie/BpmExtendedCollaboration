package de.gravitex.bpm.helper;

import lombok.Data;

@Data
public class ProcessCallbackKey {
	
	private ProcessCallbackKey() {
		// ...
	}

	private String processId;
	
	private String callbackId;
	
	public static ProcessCallbackKey fromValues(String aProcessId, String aCallbackId) {
		ProcessCallbackKey processCallbackKey = new ProcessCallbackKey();
		processCallbackKey.setProcessId(aProcessId);
		processCallbackKey.setCallbackId(aCallbackId);
		return processCallbackKey;
	}
}