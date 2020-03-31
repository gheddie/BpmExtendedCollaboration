package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.io.Serializable;

import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;
import de.gravitex.bpm.helper.util.businesskey.base.ProcessIdentifier;
import lombok.Data;

@Data
public class WaggonDamageInfo implements ProcessIdentifier, Serializable {

	private static final long serialVersionUID = -5177733383891341060L;

	private WaggonErrorCode waggonErrorCode;

	private Integer assumedRepairDurationInHours = null;

	private transient Waggon waggon;

	private transient WaggonDamage waggonDamage;

	public boolean isCritical() {
		return waggonErrorCode.isCritical();
	}

	public static WaggonDamageInfo fromValues(String waggonErrorCodeValue) {
		WaggonDamageInfo waggonDamageInfo = new WaggonDamageInfo();
		waggonDamageInfo.setWaggonErrorCode(WaggonErrorCode.valueOf(waggonErrorCodeValue));
		return waggonDamageInfo;
	}

	@Override
	public String generateProcessIdentifier() {
		return BusinesskeyGenerator.buildConcatedIdentifier(waggon.getWaggonNumber(), waggonDamage.getDamageIdentifier(),
				waggonErrorCode.toString());
	}
}