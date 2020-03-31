package de.gravitex.bpm.helper.entity.traindepartmentnew;

import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;
import de.gravitex.bpm.helper.util.businesskey.base.ProcessIdentifier;
import lombok.Data;

@Data
public class WaggonDamageRepairAssumption implements ProcessIdentifier {

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

	@Override
	public String generateProcessIdentifier() {
		return BusinesskeyGenerator.buildConcatedIdentifier(waggonNumber, damageIdentifier, waggonErrorCode.toString());
	}
}