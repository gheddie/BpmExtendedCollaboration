package de.gravitex.bpm.helper;

import java.util.HashMap;
import java.util.UUID;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class ProcessRegistry {

	private static ProcessRegistry instance;

	private HashMap<ProcessCallbackKey, ProcessCallback> callbacks = new HashMap<ProcessCallbackKey, ProcessCallback>();

	private ProcessRegistry() {
		// ...
	}

	public static ProcessRegistry getInstance() {
		if (instance == null) {
			instance = new ProcessRegistry();
		}
		return instance;
	}

	public ProcessInstance startProcessInstanceByMessage(DelegateExecution delegateExecution, String messageName,
			String callbackIdentifier, String callbackMessageName) {
		ProcessInstance processInstance = delegateExecution.getProcessEngine().getRuntimeService()
				.startProcessInstanceByMessage(messageName);
		registerCallback(processInstance.getId(), callbackIdentifier, callbackMessageName, delegateExecution.getBusinessKey());
		return processInstance;
	}
	
	public ProcessInstance startProcessInstanceByKey(ProcessEngineServices processEngine, String processDefinitionKey, HashMap<String, Object> variables) {
		return processEngine.getRuntimeService().startProcessInstanceByKey(
				processDefinitionKey, generateBusinessKey(processDefinitionKey),
				variables);
	}

	private String generateBusinessKey(String processDefinitionKey) {
		String businessKey = processDefinitionKey + "@" + UUID.randomUUID();
		return businessKey;
	}

	private void registerCallback(String aProcessId, String aCallbackId, String aCallbackMessageName, String aMasterProcessBusinessKey) {
		callbacks.put(ProcessCallbackKey.fromValues(aProcessId, aCallbackId),
				ProcessCallback.fromValues(aCallbackMessageName, aMasterProcessBusinessKey));
	}

	public ProcessCallback getCallback(String aProcessId, String aCallbackId) {
		return callbacks.get(ProcessCallbackKey.fromValues(aProcessId, aCallbackId));
	}
}