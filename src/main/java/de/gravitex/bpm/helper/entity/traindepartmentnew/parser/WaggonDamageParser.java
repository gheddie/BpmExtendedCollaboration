package de.gravitex.bpm.helper.entity.traindepartmentnew.parser;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.bpm.helper.entity.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonDamage;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonDamageInfo;

public class WaggonDamageParser {

	private transient Waggon waggon;

	public List<WaggonDamage> parse(Waggon aWaggon, String waggonData) {
		this.waggon = aWaggon;
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

	public WaggonDamage getWaggonDamage(String damageIdentifier, String errorCodes) {
		List<WaggonDamageInfo> damageInfos = getDamageInfos(errorCodes);
		WaggonDamage waggonDamage = WaggonDamage.fromValues(damageIdentifier, damageInfos);
		for (WaggonDamageInfo waggonDamageInfo : damageInfos) {
			waggonDamageInfo.setWaggonDamage(waggonDamage);
			waggonDamageInfo.setWaggon(waggon);
		}
		return waggonDamage;
	}

	private List<WaggonDamageInfo> getDamageInfos(String errorCodes) {
		List<WaggonDamageInfo> waggonDamageInfos = new ArrayList<WaggonDamageInfo>();
		for (String waggonErrorCodeValue : errorCodes.split(",")) {
			waggonDamageInfos.add(WaggonDamageInfo.fromValues(waggonErrorCodeValue));
		}
		return waggonDamageInfos;
	}
}