package de.gravitex.bpm.helper;

import java.io.Serializable;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;

import lombok.Data;

@Data
public class ProcessCallback implements Serializable {

	private static final long serialVersionUID = -6128104869011233840L;

	private String targetMessageName;

	private String targetProcessBusinessKey;

	private ProcessCallback() {
		// ...
	}

	public static ProcessCallback fromValues(String aTargetMessageName, String aMasterProcessBusinessKey) {
		ProcessCallback processCallback = new ProcessCallback();
		processCallback.setTargetMessageName(aTargetMessageName);
		processCallback.setTargetProcessBusinessKey(aMasterProcessBusinessKey);
		return processCallback;
	}

	public void execute(DelegateExecution execution) {
		execution.getProcessEngine().getRuntimeService().correlateMessage(targetMessageName, targetProcessBusinessKey);
	}
}