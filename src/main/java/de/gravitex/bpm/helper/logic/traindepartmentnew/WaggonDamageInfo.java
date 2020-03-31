package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.io.Serializable;

import lombok.Data;

@Data
public class WaggonDamageInfo implements Serializable {

	private static final long serialVersionUID = -5177733383891341060L;

	private WaggonErrorCode waggonErrorCode;
	
	private Integer assumedRepairDurationInHours = null;

	public boolean isCritical() {
		return waggonErrorCode.isCritical();
	}

	public static WaggonDamageInfo fromValues(String waggonErrorCodeValue) {
		WaggonDamageInfo waggonDamageInfo = new WaggonDamageInfo();
		waggonDamageInfo.setWaggonErrorCode(WaggonErrorCode.valueOf(waggonErrorCodeValue));
		return waggonDamageInfo;
	}
}