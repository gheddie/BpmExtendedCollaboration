package de.gravitex.bpm.helper.util.businesskey;

import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;

public class DefaultBusinesskeyGenerator extends BusinesskeyGenerator<Object> {

	@Override
	protected Object getAdditionalValueObject() {
		return null;
	}
}