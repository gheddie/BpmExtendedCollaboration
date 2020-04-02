package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

import de.gravitex.bpm.helper.entity.traindepartmentnew.DepartureOrder;
import de.gravitex.bpm.helper.entity.traindepartmentnew.TrainDepartureData;
import de.gravitex.bpm.helper.entity.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.enumeration.traindepartmentnew.DepartureOrderState;

public class TrainDepartureLogic {
	
	private static final Logger logger = Logger.getLogger(TrainDepartureLogic.class);

	private static TrainDepartureLogic instance;
	
	HashMap<String, DepartureOrder> departureOrders = new HashMap<String, DepartureOrder>();
	
	private TrainDepartureLogic() {
		// ...
	}

	public static TrainDepartureLogic getInstance() {
		if (instance == null) {
			instance = new TrainDepartureLogic();
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

	public DepartureOrder createDepartureOrder(String businessKey, Collection<Waggon> aWaggons) {
		DepartureOrder departureOrder = null;
		if (!checkActiveWaggons(aWaggons)) {
			departureOrder = DepartureOrder.fromWaggons(aWaggons, businessKey, DepartureOrderState.SUSPENDED);	
		} else {
			departureOrder = DepartureOrder.fromWaggons(aWaggons, businessKey, DepartureOrderState.ACTIVE);
		}
		logger.info("created departure order for business key [" + businessKey + "]: " + departureOrder);
		departureOrders.put(departureOrder.getBusinessKey(), departureOrder);
		return departureOrder;
	}

	private boolean checkActiveWaggons(Collection<Waggon> aWaggons) {
		HashSet<String> activeWaggons = collectActiveWaggons();
		for (Waggon waggon : aWaggons) {
			if (activeWaggons.contains(waggon.getWaggonNumber())) {
				return false;
			}
		}
		return true;
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

	public DepartureOrder getDepartureOrder(String businessKey) {
		return departureOrders.get(businessKey);
	}
}