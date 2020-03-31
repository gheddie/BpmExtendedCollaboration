package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.gravitex.bpm.helper.logic.traindepartmentnew.parser.WaggonDamageParser;
import lombok.Data;

@Data
public class Waggon implements Serializable {

	private static final long serialVersionUID = -5313835697995379746L;
	
	private String waggonNumber;
	
	private HashMap<String, WaggonDamage> waggonDamages = new HashMap<String, WaggonDamage>();
	
	private Waggon() {
		// ...
	}

	public static Waggon fromWaggonData(String waggonData) {
		Waggon waggon = new Waggon();
		String[] splitted = waggonData.split("@");
		waggon.setWaggonNumber(splitted[0]);
		if (splitted.length > 1) {
			waggon.applyWaggonDamages(WaggonDamageParser.parse(splitted[1]));			
		}
		return waggon;
	}

	private void applyWaggonDamages(List<WaggonDamage> aWaggonDamages) {
		for (WaggonDamage waggonDamage : aWaggonDamages) {
			waggonDamages.put(waggonDamage.getDamageIdentifier(), waggonDamage);
		}
	}

	public boolean isCritical() {
		for (WaggonDamage waggonDamage : waggonDamages.values()) {
			if (waggonDamage.isCritical()) {
				return true;		
			}
		}
		return false;
	}

	public boolean allCriticalDamagesAssumed() {
		for (WaggonDamage waggonDamage : waggonDamages.values()) {
			if (!waggonDamage.allCriticalDamagesAssumed()) {
				return false;				
			}
		}
		return true;
	}

	public void updateRepairAssumement(WaggonDamageRepairAssumption waggonDamageRepairAssumption) {
		waggonDamages.get(waggonDamageRepairAssumption.getDamageIdentifier()).updateRepairAssumement(waggonDamageRepairAssumption);
	}
}