package de.gravitex.bpm.helper.entity.traindepartmentnew;

import java.util.Collection;

import de.gravitex.bpm.helper.enumeration.traindepartmentnew.DepartureOrderState;
import lombok.Data;

@Data
public class DepartureOrder {
	
	private Collection<Waggon> waggons;
	
	private DepartureOrderState departureOrderState;
	
	private String businessKey;

	private DepartureOrder() {
		// ...
	}

	public static DepartureOrder fromWaggons(Collection<Waggon> aWaggons, String aBusinessKey, DepartureOrderState aDepartureOrderState) {
		DepartureOrder departureOrder = new DepartureOrder();
		departureOrder.setWaggons(aWaggons);
		departureOrder.setDepartureOrderState(aDepartureOrderState);
		departureOrder.setBusinessKey(aBusinessKey);
		return departureOrder;
	}
}