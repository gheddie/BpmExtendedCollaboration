package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import de.gravitex.bpm.helper.entity.traindepartmentnew.DepartureOrder;
import de.gravitex.bpm.helper.entity.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.enumeration.traindepartmentnew.DepartureOrderState;
import de.gravitex.bpm.helper.exception.traindepartmentnew.CreateDepartureOrderException;

public class TrainDepartmentLogic {

	private static TrainDepartmentLogic instance;
	
	HashMap<String, DepartureOrder> departureOrders = new HashMap<String, DepartureOrder>();
	
	private TrainDepartmentLogic() {
		// ...
	}

	public static TrainDepartmentLogic getInstance() {
		if (instance == null) {
			instance = new TrainDepartmentLogic();
		}
		return instance;
	}

	public Collection<DepartureOrder> getDepartureOrders(DepartureOrderState departureOrderState) {
		Collection<DepartureOrder> result = new ArrayList<DepartureOrder>();
		for (DepartureOrder departureOrder : departureOrders.values()) {
			if (departureOrder.getDepartureOrderState().equals(departureOrderState)) {
				result.add(departureOrder);
			}
		}
		return result;
	}

	public void createDepartureOrder(String businessKey, Collection<Waggon> aWaggons) throws CreateDepartureOrderException {
		checkActiveWaggons(aWaggons);
		departureOrders.put(businessKey, DepartureOrder.fromWaggons(aWaggons));
	}

	private void checkActiveWaggons(Collection<Waggon> aWaggons) throws CreateDepartureOrderException {
		HashSet<String> activeWaggons = collectActiveWaggons();
		for (Waggon waggon : aWaggons) {
			if (activeWaggons.contains(waggon.getWaggonNumber())) {
				throw new CreateDepartureOrderException(
						"waggon " + waggon.getWaggonNumber() + " is already in an active departure order!!");
			}
		}
	}

	private HashSet<String> collectActiveWaggons() {
		HashSet<String> activeWaggonNumbers = new HashSet<String>();
		for (DepartureOrder activeDepartureOrder : getDepartureOrders(DepartureOrderState.ACTIVE)) {
			for (Waggon activeWaggon : activeDepartureOrder.getWaggons()) {
				activeWaggonNumbers.add(activeWaggon.getWaggonNumber());				
			}
		}
		return activeWaggonNumbers;
	}

	public void reset() {
		departureOrders = new HashMap<String, DepartureOrder>();
	}
}