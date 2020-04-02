package de.gravitex.bpm.helper.logic.traindepartmentnew;

import java.util.HashMap;

import de.gravitex.bpm.helper.entity.traindepartmentnew.DepartureOrder;
import lombok.Data;

@Data
public class DepartureOrderContainer {

	private DepartureOrder actualDepartureOrder;
	
	private String businessKey;
	
	private HashMap<Integer, DepartureOrder> history = new HashMap<Integer, DepartureOrder>();
	
	private Integer historyIndex = 0;
	
	private DepartureOrderContainer() {
		// ...
	}

	public static DepartureOrderContainer fromBusinessKey(String aBusinessKey) {
		DepartureOrderContainer departureOrderContainer = new DepartureOrderContainer();
		departureOrderContainer.setBusinessKey(aBusinessKey);
		return departureOrderContainer;
	}

	public DepartureOrderContainer accept(DepartureOrder aDepartureOrder) {
		historize();
		actualDepartureOrder = aDepartureOrder;
		return this;
	}

	private void historize() {
		if (actualDepartureOrder == null) {
			return;
		}
		history.put(historyIndex++, actualDepartureOrder);
	}

	public int getHistoryLevel() {
		return history.values().size();
	}
}