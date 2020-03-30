package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.gravitex.bpm.helper.logic.traindepartmentnew.parser.WaggonDamageParser;
import lombok.Data;

@Data
public class Waggon implements Serializable {

	private static final long serialVersionUID = -5313835697995379746L;
	
	private String waggonNumber;
	
	private List<WaggonDamage> waggonDamages = new ArrayList<WaggonDamage>();
	
	private Integer repairDurationInHours = null;
	
	private Waggon() {
		// ...
	}

	public static Waggon fromWaggonData(String waggonData) {
		Waggon waggon = new Waggon();
		String[] splitted = waggonData.split("@");
		waggon.setWaggonNumber(splitted[0]);
		if (splitted.length > 1) {
			waggon.setWaggonDamages(WaggonDamageParser.parse(splitted[1]));			
		}
		return waggon;
	}

	public boolean isCritical() {
		for (WaggonDamage waggonDamage : waggonDamages) {
			if (waggonDamage.isCritical()) {
				return true;		
			}
		}
		return false;
	}
}