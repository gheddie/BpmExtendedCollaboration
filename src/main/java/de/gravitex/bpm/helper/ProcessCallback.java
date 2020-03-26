package de.gravitex.bpm.helper;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateTask;

import lombok.Data;

@Data
public class ProcessCallback {

	private String targetMessageName;

	private String masterProcessBusinessKey;

	private ProcessCallback() {
		// ...
	}

	public static ProcessCallback fromValues(String aTargetMessageName, String aMasterProcessBusinessKey) {
		ProcessCallback processCallback = new ProcessCallback();
		processCallback.setTargetMessageName(aTargetMessageName);
		processCallback.setMasterProcessBusinessKey(aMasterProcessBusinessKey);
		return processCallback;
	}

	public void execute(DelegateTask delegateTask) {
		delegateTask.getExecution().getProcessEngine().getRuntimeService().correlateMessage(targetMessageName,
				masterProcessBusinessKey);
	}
}