package de.gravitex.bpm.helper.util;

import java.util.UUID;

import org.camunda.bpm.engine.RuntimeService;

import de.gravitex.bpm.helper.ProcessRegistry;
import de.gravitex.bpm.helper.constant.ProcessConstants;

public class ProcessHelper {

	public static String createBusinessKey(String processDefinitionKey) {
		return processDefinitionKey + "@" + UUID.randomUUID();
	}

	public static ProcessRegistry getProcessRegistry(RuntimeService runtimeService, String executionId) {
		if (runtimeService.getVariable(executionId, ProcessConstants.Common.VAR.VAR_PROCESS_REGISTRY) == null) {
			runtimeService.setVariable(executionId, ProcessConstants.Common.VAR.VAR_PROCESS_REGISTRY,
					new ProcessRegistry());
		}
		return (ProcessRegistry) runtimeService.getVariable(executionId,
				ProcessConstants.Common.VAR.VAR_PROCESS_REGISTRY);
	}
}