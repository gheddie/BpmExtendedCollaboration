package de.gravitex.bpm.helper.util;

import java.util.HashMap;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.util.businesskey.DefaultBusinesskeyGenerator;
import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;
import de.gravitex.bpm.helper.util.businesskey.collaboration.AnotherSlaveBusinesskeyGenerator;

public class ProcessHelper {
	
	private static final String DEFAULT_BKG = "DEFAULT_BKG";
	
	private static final HashMap<String, BusinesskeyGenerator> businesskeyGenerators = new HashMap<String, BusinesskeyGenerator>();
	static {
		businesskeyGenerators.put(ProcessConstants.Collaboration.AnotherSlave.DEF.DEF_ANOTHER_SLAVE_PROCESS, new AnotherSlaveBusinesskeyGenerator());
		businesskeyGenerators.put(DEFAULT_BKG, new DefaultBusinesskeyGenerator());
	}

	public static String generateBusinessKey(String processDefinitionKey, HashMap<String, Object> variables, String parentBusinessKey) {
		BusinesskeyGenerator businesskeyGenerator = businesskeyGenerators.get(processDefinitionKey);
		if (businesskeyGenerator == null) {
			businesskeyGenerator = businesskeyGenerators.get(DEFAULT_BKG);
		}
		return businesskeyGenerator.generateBusinessKey(processDefinitionKey, variables, parentBusinessKey);
	}

	public static ProcessInstance startProcessInstanceByKey(ProcessEngineServices processEngine, String processDefinitonKey,
			HashMap<String, Object> variables, String parentBusinessKey) {
		String businessKey = ProcessHelper.generateBusinessKey(processDefinitonKey, variables, parentBusinessKey);
		ProcessInstance masterProcessInstance = processEngine.getRuntimeService().startProcessInstanceByKey(
				processDefinitonKey, businessKey, variables);
		return masterProcessInstance;
	}
	
	public static ProcessInstance startProcessInstanceByMessage(ProcessEngineServices processEngine, String processDefinitionKey,
			String messageName, HashMap<String, Object> variables, String parentBusinessKey) {
		String businessKey = ProcessHelper.generateBusinessKey(processDefinitionKey, variables, parentBusinessKey);
		return processEngine.getRuntimeService().startProcessInstanceByMessage(messageName,
				businessKey, variables);
	}
}