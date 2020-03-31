package de.gravitex.bpm.helper.logic.traindepartmentnew.parser;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonDamage;
import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonDamageInfo;

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
		return WaggonDamage.fromValues(damageIdentifier, getDamageInfos(errorCodes));
	}

	private static List<WaggonDamageInfo> getDamageInfos(String errorCodes) {
		List<WaggonDamageInfo> waggonDamageInfos = new ArrayList<WaggonDamageInfo>();
		for (String waggonErrorCodeValue : errorCodes.split(",")) {
			waggonDamageInfos.add(WaggonDamageInfo.fromValues(waggonErrorCodeValue));
		}
		return waggonDamageInfos;
	}
}