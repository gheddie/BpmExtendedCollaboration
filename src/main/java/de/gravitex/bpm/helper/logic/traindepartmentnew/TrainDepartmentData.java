package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class TrainDepartmentData implements Serializable {
	
	private static final long serialVersionUID = 9130404831557192719L;
	
	private HashMap<String, Waggon> waggons = new HashMap<String, Waggon>();

	public TrainDepartmentData() {
		// ...
	}

	public Collection<Waggon> getWaggonList() {
		return waggons.values();
	}

	public TrainDepartmentData addWaggons(List<Waggon> aWaggons) {
		for (Waggon waggon : aWaggons) {
			waggons.put(waggon.getWaggonNumber(), waggon);
		}
		return this;
	}
}