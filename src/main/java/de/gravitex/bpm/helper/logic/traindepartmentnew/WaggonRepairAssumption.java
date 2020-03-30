package de.gravitex.bpm.helper.logic.traindepartmentnew;

import lombok.Data;

@Data
public class WaggonRepairAssumption {
	
	private String waggonNumber;
	
	private int repairDurationInHours;
	
	private WaggonRepairAssumption() {
		// ...
	}

	public static WaggonRepairAssumption fromValues(String aWaggonNumber, int aRepairDurationInHours) {
		WaggonRepairAssumption waggonRepairAssumption = new WaggonRepairAssumption();
		waggonRepairAssumption.setWaggonNumber(aWaggonNumber);
		waggonRepairAssumption.setRepairDurationInHours(aRepairDurationInHours);
		return waggonRepairAssumption;
	}
}