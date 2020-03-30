package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class WaggonDamage implements Serializable {
	
	private static final long serialVersionUID = 2595023961377763277L;
	
	private String identifier;

	private WaggonDamage() {
		// ...
	}

	private List<WaggonErrorCode> waggonErrorCodes = new ArrayList<WaggonErrorCode>();

	public static WaggonDamage fromValues(String aIdentifier, List<WaggonErrorCode> aWaggonErrorCodes) {
		WaggonDamage waggonDamage = new WaggonDamage();
		waggonDamage.setIdentifier(aIdentifier);
		waggonDamage.setWaggonErrorCodes(aWaggonErrorCodes);
		return waggonDamage;
	}

	public boolean isCritical() {
		for (WaggonErrorCode waggonErrorCode : waggonErrorCodes) {
			if (waggonErrorCode.isCritical()) {
				return true;
			}
		}
		return false;
	}
}