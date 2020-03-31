package de.gravitex.bpm.helper.util.businesskey.traindepartment;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonDamageInfo;
import de.gravitex.bpm.helper.util.businesskey.base.BusinesskeyGenerator;

public class RepairFacilityBusinesskeyGenerator extends BusinesskeyGenerator<WaggonDamageInfo> {

	@Override
	protected WaggonDamageInfo getAdditionalValueObject() {
		return (WaggonDamageInfo) getVariables().get(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_SINGLE_WAGGON_DAMAGE_TO_ASSUME);
	}
}