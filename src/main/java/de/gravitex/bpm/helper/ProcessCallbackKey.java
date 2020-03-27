package de.gravitex.bpm.helper;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProcessCallbackKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6395645363250913792L;

	private String processId;
	
	private String callbackId;
	
	private ProcessCallbackKey() {
		// ...
	}
	
	public static ProcessCallbackKey fromValues(String aProcessId, String aCallbackId) {
		ProcessCallbackKey processCallbackKey = new ProcessCallbackKey();
		processCallbackKey.setProcessId(aProcessId);
		processCallbackKey.setCallbackId(aCallbackId);
		return processCallbackKey;
	}
}