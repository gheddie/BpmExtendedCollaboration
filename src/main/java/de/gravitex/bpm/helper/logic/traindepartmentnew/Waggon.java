package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Waggon implements Serializable {

	private static final long serialVersionUID = -5313835697995379746L;
	
	private String waggonNumber;
	
	private List<WaggonErrorCode> waggonErrorCodes = new ArrayList<WaggonErrorCode>();
	
	private Waggon() {
		// ...
	}

	public static Waggon fromWaggonData(String waggonData) {
		Waggon waggon = new Waggon();
		String[] splitted = waggonData.split("@");
		waggon.setWaggonNumber(splitted[0]);
		if (splitted.length > 1) {
			String[] errorCodes = splitted[1].split(",");
			for (String errorCode : errorCodes) {
				waggon.getWaggonErrorCodes().add(WaggonErrorCode.valueOf(errorCode));
			}
		}
		return waggon;
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