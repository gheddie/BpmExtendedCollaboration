package de.gravitex.bpm.helper.entity.traindepartmentnew;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class WaggonDamage implements Serializable {

	private static final long serialVersionUID = 2595023961377763277L;

	private String damageIdentifier;

	private WaggonDamage() {
		// ...
	}

	private HashMap<WaggonErrorCode, WaggonDamageInfo> waggonDamageInfos = new HashMap<WaggonErrorCode, WaggonDamageInfo>();

	public static WaggonDamage fromValues(String aDamageIdentifier, List<WaggonDamageInfo> aWaggonDamageInfos) {
		WaggonDamage waggonDamage = new WaggonDamage();
		waggonDamage.setDamageIdentifier(aDamageIdentifier);
		waggonDamage.applyWaggonErrorInfos(aWaggonDamageInfos);
		return waggonDamage;
	}

	private void applyWaggonErrorInfos(List<WaggonDamageInfo> aWaggonDamageInfos) {
		for (WaggonDamageInfo waggonDamageInfo : aWaggonDamageInfos) {
			waggonDamageInfos.put(waggonDamageInfo.getWaggonErrorCode(), waggonDamageInfo);
		}
	}

	public boolean isCritical() {
		for (WaggonDamageInfo waggonDamageInfo : waggonDamageInfos.values()) {
			if (waggonDamageInfo.isCritical()) {
				return true;
			}
		}
		return false;
	}

	public boolean allCriticalDamagesAssumed() {
		for (WaggonDamageInfo waggonDamageInfo : waggonDamageInfos.values()) {
			if (waggonDamageInfo.isCritical() && waggonDamageInfo.getAssumedRepairDurationInHours() == null) {
				return false;
			}
		}
		return true;
	}

	public void updateRepairAssumement(WaggonDamageRepairAssumption waggonDamageRepairAssumption) {
		waggonDamageInfos.get(waggonDamageRepairAssumption.getWaggonErrorCode())
				.setAssumedRepairDurationInHours(waggonDamageRepairAssumption.getAssumedRepairDurationInHours());
	}
}