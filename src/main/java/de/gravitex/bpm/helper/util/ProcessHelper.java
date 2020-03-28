package de.gravitex.bpm.helper.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class ProcessHelper {

	public static String createBusinessKey(String processDefinitionKey) {
		return processDefinitionKey + "@" + UUID.randomUUID();
	}

	public static ProcessInstance startProcessInstanceByKey(ProcessEngineServices processEngine, String processDefinitonKey,
			HashMap<String, Object> variables) {
		String businessKey = ProcessHelper.createBusinessKey(processDefinitonKey);
		ProcessInstance masterProcessInstance = processEngine.getRuntimeService().startProcessInstanceByKey(
				processDefinitonKey, businessKey, variables);
		return masterProcessInstance;
	}
	
	public static void startProcessInstanceByMessage(ProcessEngineServices processEngine, String processDefinitionKey,
			String messageName, Map<String, Object> variables) {
		String businessKey = ProcessHelper.createBusinessKey(processDefinitionKey);
		processEngine.getRuntimeService().startProcessInstanceByMessage(messageName,
				ProcessHelper.createBusinessKey(businessKey), variables);
	}
}