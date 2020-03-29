package de.gravitex.bpm.helper.util.businesskey.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;
import de.gravitex.bpm.helper.util.businesskey.matcher.exception.BusinessKeyMatcherException;
import lombok.Data;

@Data
public class BusinessKeyMatcher {

	private String processDefinitionKey;

	private List<AbstractBusinessKeyMatcher> matchers = new ArrayList<BusinessKeyMatcher.AbstractBusinessKeyMatcher>();

	private BusinessKeyMatcher() {
		// ...
	}

	public static BusinessKeyMatcher forProcessDefinitionKey(String processDefinitionKey) {
		BusinessKeyMatcher businessKeyMatcher = new BusinessKeyMatcher();
		businessKeyMatcher.setProcessDefinitionKey(processDefinitionKey);
		return businessKeyMatcher;
	}

	public List<ProcessInstance> listResult(RuntimeService runtimeService) {
		List<ProcessInstance> potentialInstances = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(processDefinitionKey).list();
		List<ProcessInstance> result = new ArrayList<ProcessInstance>();
		for (ProcessInstance potentialInstance : potentialInstances) {
			if (matchInternal(potentialInstance)) {
				result.add(potentialInstance);
			}
		}
		return result;
	}

	private boolean matchInternal(ProcessInstance potentialInstance) {
		for (AbstractBusinessKeyMatcher matcher : matchers) {
			if (!(matcher.matches(potentialInstance.getBusinessKey()))) {
				return false;
			}
		}
		return true;
	}

	public ProcessInstance singleResult(RuntimeService runtimeService) {
		List<ProcessInstance> result = listResult(runtimeService);
		if (result == null || result.size() != 1) {
			throw new BusinessKeyMatcherException();
		}
		return result.get(0);
	}

	public BusinessKeyMatcher withParentBusinessKey(String parentBusinessKey) {
		matchers.add(new ParentBusinessKeyMatcher(parentBusinessKey));
		return this;
	}

	public BusinessKeyMatcher withAdditionalValue(String additionalValue) {
		matchers.add(new AdditionalValueBusinessKeyMatcher(additionalValue));
		return this;
	}

	// ---

	@Data
	private abstract class AbstractBusinessKeyMatcher {

		private String value;

		private AbstractBusinessKeyMatcher(String aValue) {
			super();
			this.value = aValue;
		}

		protected abstract boolean matches(String businessKey);
		
		protected boolean valuesEqual(String s1, String s2) {
			
			if (s1 == null && s2 == null) {
				return true;	
			}
			if (s1 == null && s2 != null) {
				return false;	
			}
			if (s1 != null && s2 == null) {
				return false;	
			}
			return s1.equals(s2);
		}
	}

	private class ParentBusinessKeyMatcher extends AbstractBusinessKeyMatcher {

		private ParentBusinessKeyMatcher(String aValue) {
			super(aValue);
		}

		@Override
		protected boolean matches(String businessKey) {
			String parentBusinessKey = BusinesskeyGenerator.getParentBusinessKey(businessKey);
			return valuesEqual(parentBusinessKey, getValue());
		}
	}

	private class AdditionalValueBusinessKeyMatcher extends AbstractBusinessKeyMatcher {

		private AdditionalValueBusinessKeyMatcher(String aValue) {
			super(aValue);
		}

		@Override
		protected boolean matches(String businessKey) {
			String additionalValue = BusinesskeyGenerator.getAdditionalValue(businessKey);
			return valuesEqual(additionalValue, getValue());
		}
	}
}