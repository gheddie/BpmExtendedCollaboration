package de.gravitex.bpm.helper.util.businesskey.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import lombok.Data;

@Data
public abstract class BusinesskeyGenerator {
	
	private static final Logger logger = Logger.getLogger(BusinesskeyGenerator.class);

	private String processDefinitionKey;
	
	public static final String KEY_PROCESS_DEFINITION = "KEY_PROCESS_DEFINITION";
	
	public static final String KEY_UUID = "KEY_UUID";
	
	public static final String KEY_ADDITIONAL_VALUE = "KEY_ADDITIONAL_VALUE";
	
	public static final String KEY_PARENT_BUSINSS_KEY = "KEY_PARENT_BUSINSS_KEY";
	
	private static final String BUSINESS_KEY_COMPONENT_DIVIDER = "@";
	
	private static final String BUSINESS_KEY_PBKEY_DIVIDER = "#";
	
	private HashMap<String, Object> variables;

	public String generateBusinessKey(String aProcessDefinitionKey, HashMap<String, Object> aVariables, String aParentBusinessKey) {
		this.processDefinitionKey = aProcessDefinitionKey;
		this.variables = aVariables;
		StringBuffer buffer = new StringBuffer();
		List<Object> businesskeyComponents = getBusinesskeyComponents();
		int index = 0;
		for (Object component : businesskeyComponents) {
			buffer.append(component);
			if (index < businesskeyComponents.size() - 1) {
				buffer.append(BUSINESS_KEY_COMPONENT_DIVIDER);				
			}
			index++;
		}
		if (aParentBusinessKey != null) {
			buffer.append(BUSINESS_KEY_PBKEY_DIVIDER).append(aParentBusinessKey);
		}
		String result = buffer.toString();
		return result;
	}

	protected List<Object> getBusinesskeyComponents() {
		return Arrays.asList(new Object[] {processDefinitionKey, UUID.randomUUID()});
	}

	public static HashMap<String, String> splitComponents(String businessKey) {
		HashMap<String, String> result = new HashMap<String, String>();
		String[] splitPbk = businessKey.split(BUSINESS_KEY_PBKEY_DIVIDER);
		if (splitPbk.length > 1) {
			result.put(KEY_PARENT_BUSINSS_KEY, splitPbk[1]);
		}
		String[] splitDefault = splitPbk[0].split(BUSINESS_KEY_COMPONENT_DIVIDER);
		result.put(KEY_PROCESS_DEFINITION, splitDefault[0]);
		result.put(KEY_UUID, splitDefault[1]);
		if (splitDefault.length > 2) {
			result.put(KEY_ADDITIONAL_VALUE, splitDefault[2]);	
		}
		return result;
	}

	public static String getParentBusinessKey(String businessKey) {
		return splitComponents(businessKey).get(KEY_PARENT_BUSINSS_KEY);
	}

	public static String getAdditionalValue(String businessKey) {
		return splitComponents(businessKey).get(KEY_ADDITIONAL_VALUE);
	}
}