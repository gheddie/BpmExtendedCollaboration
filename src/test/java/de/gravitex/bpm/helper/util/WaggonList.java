package de.gravitex.bpm.helper.util;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.bpm.helper.entity.traindepartmentnew.Waggon;
import lombok.Data;

@Data
public class WaggonList {
	
	private List<Waggon> waggonList = new ArrayList<Waggon>();

	public WaggonList withWaggonData(String waggonData) {
		waggonList.add(Waggon.fromWaggonData(waggonData));
		return this;
	}
}