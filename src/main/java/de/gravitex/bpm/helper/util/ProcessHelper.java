package de.gravitex.bpm.helper.util;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.entity.traindepartmentnew.TrainDepartmentData;
import de.gravitex.bpm.helper.util.businesskey.DefaultBusinesskeyGenerator;
import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;
import de.gravitex.bpm.helper.util.businesskey.collaboration.AnotherSlaveBusinesskeyGenerator;
import de.gravitex.bpm.helper.util.businesskey.traindepartment.RepairFacilityBusinesskeyGenerator;

public class ProcessHelper {
	
	private static final Logger logger = Logger.getLogger(ProcessHelper.class);
	
	private static final String DEFAULT_BKG = "DEFAULT_BKG";
	
	private static final HashMap<String, BusinesskeyGenerator> businesskeyGenerators = new HashMap<String, BusinesskeyGenerator>();
	static {
		// collaboration
		businesskeyGenerators.put(ProcessConstants.Collaboration.AnotherSlave.DEF.DEF_ANOTHER_SLAVE_PROCESS, new AnotherSlaveBusinesskeyGenerator());
		
		// traindepartment
		businesskeyGenerators.put(ProcessConstants.Trainpartment.RepairFacility.DEF.DEF_REPAIR_FACILITY_PROCESS, new RepairFacilityBusinesskeyGenerator());
		
		// default
		businesskeyGenerators.put(DEFAULT_BKG, new DefaultBusinesskeyGenerator());
	}

	public static String generateBusinessKey(String processDefinitionKey, HashMap<String, Object> variables, String parentBusinessKey) {
		BusinesskeyGenerator businesskeyGenerator = businesskeyGenerators.get(processDefinitionKey);
		if (businesskeyGenerator == null) {
			businesskeyGenerator = businesskeyGenerators.get(DEFAULT_BKG);
		}
		String businessKey = businesskeyGenerator.generateBusinessKey(processDefinitionKey, variables, parentBusinessKey);
		logger.info("generated business key for definition '" + processDefinitionKey + "': " + businessKey);
		return businessKey;
	}

	public static ProcessInstance startProcessInstanceByKey(ProcessEngineServices processEngine, String processDefinitonKey,
			HashMap<String, Object> variables, String parentBusinessKey) {
		String businessKey = ProcessHelper.generateBusinessKey(processDefinitonKey, variables, parentBusinessKey);
		// provide parent execution business key
		if (parentBusinessKey != null) {
			if (variables == null) {
				variables = new HashMap<String, Object>();
			}
			variables.put(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK, parentBusinessKey);			
		}
		ProcessInstance masterProcessInstance = processEngine.getRuntimeService().startProcessInstanceByKey(
				processDefinitonKey, businessKey, variables);
		return masterProcessInstance;
	}
	
	public static ProcessInstance startProcessInstanceByMessage(ProcessEngineServices processEngine, String processDefinitionKey,
			String messageName, HashMap<String, Object> variables, String parentBusinessKey) {
		String businessKey = ProcessHelper.generateBusinessKey(processDefinitionKey, variables, parentBusinessKey);
		// provide parent execution business key
		if (parentBusinessKey != null) {
			if (variables == null) {
				variables = new HashMap<String, Object>();
			}
			variables.put(ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK, parentBusinessKey);			
		}
		return processEngine.getRuntimeService().startProcessInstanceByMessage(messageName,
				businessKey, variables);
	}
	
	public static String getMasterProcessBusinessKey(DelegateExecution execution) {
		return (String) execution.getProcessEngine().getRuntimeService()
				.getVariable(execution.getId(), ProcessConstants.Common.VAR.VAR_MASTER_PROCESS_BK);
	}

	public static TrainDepartmentData getgetTrainDepartmentData(DelegateExecution execution) {
		return (TrainDepartmentData) execution.getProcessEngine().getRuntimeService()
				.getVariable(execution.getId(), ProcessConstants.Trainpartment.TrainStation.VAR.VAR_TRAIN_DEPARTMENT_DATA);
	}
}