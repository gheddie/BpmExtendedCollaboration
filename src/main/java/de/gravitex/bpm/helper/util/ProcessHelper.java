package de.gravitex.bpm.helper.util;

import java.util.UUID;

public class ProcessHelper {

	public static String createBusinessKey(String processDefinitionKey) {
		return processDefinitionKey + "@" + UUID.randomUUID();
	}
}