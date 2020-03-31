package de.gravitex.bpm.helper.entity.traindepartmentnew;

import java.util.Collection;

import de.gravitex.bpm.helper.enumeration.traindepartmentnew.DepartureOrderState;
import lombok.Data;

@Data
public class DepartureOrder {
	
	private Collection<Waggon> waggons;
	
	private DepartureOrderState departureOrderState;

	private DepartureOrder() {
		// ...
	}

	public static DepartureOrder fromWaggons(Collection<Waggon> aWaggons) {
		DepartureOrder departureOrder = new DepartureOrder();
		departureOrder.setWaggons(aWaggons);
		departureOrder.setDepartureOrderState(DepartureOrderState.ACTIVE);
		return departureOrder;
	}
}