package de.gravitex.bpm.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class ProcessRegistry implements Serializable {

	private static final long serialVersionUID = -1762063369307483469L;
	
	private HashMap<ProcessCallbackKey, ProcessCallback> callbacks = new HashMap<ProcessCallbackKey, ProcessCallback>();

	public ProcessInstance startProcessInstanceByMessage(DelegateExecution delegateExecution, String messageName) {
		String businessKey = ProcessHelper.createBusinessKey(ProcessConstants.Slave.DEF.DEF_SLAVE_PROCESS);
		ProcessInstance processInstance = delegateExecution.getProcessEngine().getRuntimeService()
				.startProcessInstanceByMessage(messageName, HashMapBuilder.create()
						.withValuePair(ProcessConstants.Common.VAR.VAR_PROCESS_REGISTRY, this).build());
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

	public void registerCallback(String aProcessId, String aCallbackId, String aCallbackMessageName, String aMasterProcessBusinessKey) {
		callbacks.put(ProcessCallbackKey.fromValues(aProcessId, aCallbackId),
				ProcessCallback.fromValues(aCallbackMessageName, aMasterProcessBusinessKey));
	}

	public ProcessCallback getCallback(String aProcessId, String aCallbackId) {
		ProcessCallback processCallback = callbacks.get(ProcessCallbackKey.fromValues(aProcessId, aCallbackId));
		return processCallback;
	}
}