package de.gravitex.bpm.helper.logic.traindepartmentnew.parser;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonDamage;
import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonErrorCode;

public class WaggonDamageParser {

	public static List<WaggonDamage> parse(String waggonData) {
		List<WaggonDamage> waggonDamages = new ArrayList<WaggonDamage>();
		if (waggonData == null || waggonData.length() == 0) {
			return waggonDamages;
		}
		String[] seperatedErrorValues = waggonData.split("#");
		for (String seperatedErrorValue : seperatedErrorValues) {
			String[] seperatedErrorKeyAndValue = seperatedErrorValue.split("=");
			waggonDamages.add(getWaggonDamage(seperatedErrorKeyAndValue[0], seperatedErrorKeyAndValue[1]));
		}
		return waggonDamages;
	}

	public static WaggonDamage getWaggonDamage(String damageIdentifier, String errorCodes) {
		return WaggonDamage.fromValues(damageIdentifier, getErrorCodes(errorCodes));
	}

	private static List<WaggonErrorCode> getErrorCodes(String errorCodes) {
		List<WaggonErrorCode> errorCodeList = new ArrayList<WaggonErrorCode>();
		for (String waggonErrorCodeValue : errorCodes.split(",")) {
			errorCodeList.add(WaggonErrorCode.valueOf(waggonErrorCodeValue));
		}
		return errorCodeList;
	}
}