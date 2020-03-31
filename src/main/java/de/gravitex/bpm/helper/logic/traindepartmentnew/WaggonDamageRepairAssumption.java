package de.gravitex.bpm.helper.logic.traindepartmentnew;

import lombok.Data;

@Data
public class WaggonDamageRepairAssumption {

	private String waggonNumber;
	
	private String damageIdentifier;

	private WaggonErrorCode waggonErrorCode;

	private int assumedRepairDurationInHours;

	private WaggonDamageRepairAssumption() {
		// ...
	}

	public static WaggonDamageRepairAssumption fromValues(String aWaggonNumber, String aDamageIdentifier,
			WaggonErrorCode aWaggonErrorCode, int anAssumedRepairDurationInHours) {
		WaggonDamageRepairAssumption waggonDamageRepairAssumption = new WaggonDamageRepairAssumption();
		waggonDamageRepairAssumption.setWaggonNumber(aWaggonNumber);
		waggonDamageRepairAssumption.setWaggonErrorCode(aWaggonErrorCode);
		waggonDamageRepairAssumption.setAssumedRepairDurationInHours(anAssumedRepairDurationInHours);
		waggonDamageRepairAssumption.setDamageIdentifier(aDamageIdentifier);
		return waggonDamageRepairAssumption;
	}
}