package de.gravitex.bpm.helper.entity.traindepartmentnew;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import de.gravitex.bpm.helper.enumeration.traindepartmentnew.DepartureOrderState;
import de.gravitex.bpm.helper.logic.traindepartmentnew.TrainDepartureLogic;
import lombok.Data;

@Data
public class TrainDepartureData implements Serializable {
	
	private static final Logger logger = Logger.getLogger(TrainDepartureData.class);
	
	private static final long serialVersionUID = 9130404831557192719L;
	
	private HashMap<String, Waggon> waggons = new HashMap<String, Waggon>();
	
	private String businessKey;

	public TrainDepartureData() {
		// ...
	}

	public Collection<Waggon> getWaggonList() {
		return waggons.values();
	}

	public TrainDepartureData addWaggons(List<Waggon> aWaggons) {
		for (Waggon waggon : aWaggons) {
			waggons.put(waggon.getWaggonNumber(), waggon);
		}
		return this;
	}
	
	public boolean allCriticalWaggonsAssumed() {
		for (Waggon waggon : waggons.values()) {
			if (waggon.isCritical() && !waggon.allCriticalDamagesAssumed()) {
				logger.info("waggon " + waggon.getWaggonNumber() + " is critical and not all critical damages are yet repair assumed --> returning false!!");
				return false;
			}
		}
		logger.info("all critical waggons repair assumed --> returning true!!");
		return true;
	}

	public Waggon getWaggonByWaggonNumber(String aWaggonNumber) {
		return waggons.get(aWaggonNumber);
	}

	public void updateRepairAssumement(WaggonDamageRepairAssumption waggonDamageRepairAssumption) {
		waggons.get(waggonDamageRepairAssumption.getWaggonNumber()).updateRepairAssumement(waggonDamageRepairAssumption);
	}
	
	public boolean departingOrderSuspended() {
		return TrainDepartureLogic.getInstance().getDepartureOrder(businessKey).getDepartureOrderState()
				.equals(DepartureOrderState.SUSPENDED);
	}
	
	public void updateDepartingOrder(DepartureOrderState departureOrderState) {
		TrainDepartureLogic.getInstance().getDepartureOrder(businessKey).setDepartureOrderState(departureOrderState);
	}
}