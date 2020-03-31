package de.gravitex.bpm.helper.util.businesskey.traindepartment;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;

public class RepairFacilityBusinesskeyGenerator extends BusinesskeyGenerator {

	@Override
	protected List<Object> getBusinesskeyComponents() {
		List<Object> parent = super.getBusinesskeyComponents();
		Waggon waggon = (Waggon) getVariables()
				.get(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_SINGLE_WAGGON__DAMAGE_TO_ASSUME);
		List<Object> values = new ArrayList<Object>();
		for (Object o : parent) {
			values.add(o);
		}
		values.add(waggon.getWaggonNumber());
		return values;
	}
}