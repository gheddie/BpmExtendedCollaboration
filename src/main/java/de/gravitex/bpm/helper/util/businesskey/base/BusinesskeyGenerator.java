package de.gravitex.bpm.helper.util.businesskey.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import lombok.Data;

@Data
public abstract class BusinesskeyGenerator<T> {
	
	private static final Logger logger = Logger.getLogger(BusinesskeyGenerator.class);

	private String processDefinitionKey;
	
	public static final String KEY_PROCESS_DEFINITION = "KEY_PROCESS_DEFINITION";
	
	public static final String KEY_UUID = "KEY_UUID";
	
	public static final String KEY_ADDITIONAL_VALUE = "KEY_ADDITIONAL_VALUE";
	
	public static final String KEY_PARENT_BUSINSS_KEY = "KEY_PARENT_BUSINSS_KEY";
	
	private static final String BUSINESS_KEY_COMPONENT_DIVIDER = "@";
	
	private static final String BUSINESS_KEY_PBKEY_DIVIDER = "#";
	
	private static final String BUSINESS_KEY_ADDITIONAL_VALUE_DIVIDER = "_";
	
	private HashMap<String, Object> variables;

	public String generateBusinessKey(String aProcessDefinitionKey, HashMap<String, Object> aVariables, String aParentBusinessKey) {
		this.processDefinitionKey = aProcessDefinitionKey;
		this.variables = aVariables;
		List<Object> businesskeyComponents = getBusinesskeyComponents();
		StringBuffer buffer = separatedList(businesskeyComponents, BUSINESS_KEY_COMPONENT_DIVIDER);
		if (aParentBusinessKey != null) {
			buffer.append(BUSINESS_KEY_PBKEY_DIVIDER).append(aParentBusinessKey);
		}
		String result = buffer.toString();
		return result;
	}

	private static StringBuffer separatedList(List<Object> values, Object divider) {
		StringBuffer buffer = new StringBuffer();
		int index = 0;
		for (Object component : values) {
			buffer.append(component);
			if (index < values.size() - 1) {
				buffer.append(divider);				
			}
			index++;
		}
		return buffer;
	}

	protected List<Object> getBusinesskeyComponents() {
		List<Object> businesskeyComponents = new ArrayList<Object>();
		businesskeyComponents.add(processDefinitionKey);
		businesskeyComponents.add(UUID.randomUUID());
		ProcessIdentifier processIdentifier = (ProcessIdentifier) getAdditionalValueObject();
		if (processIdentifier != null) {
			businesskeyComponents.add(processIdentifier.generateProcessIdentifier());			
		}
		return businesskeyComponents;
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
	
	protected abstract T getAdditionalValueObject();

	public static String buildConcatedIdentifier(Object... values) {
		String result = separatedList(Arrays.asList(values), BUSINESS_KEY_ADDITIONAL_VALUE_DIVIDER).toString();
		return result;
	}
}