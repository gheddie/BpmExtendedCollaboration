package de.gravitex.bpm.helper.runner.traindepartmentnew;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.entity.traindepartmentnew.TrainDepartureData;
import de.gravitex.bpm.helper.entity.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonDamageInfo;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonDamageRepairAssumption;
import de.gravitex.bpm.helper.runner.base.ProcessRunner;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;
import de.gravitex.bpm.helper.util.WaggonList;
import de.gravitex.bpm.helper.util.businesskey.base.ProcessIdentifier;
import de.gravitex.bpm.helper.util.businesskey.matcher.BusinessKeyMatcher;
import lombok.Data;

@Data
public class TrainDepartmentNewRunner extends ProcessRunner<WaggonList> {

	public TrainDepartmentNewRunner(ProcessEngineServices aProcessEngine) {
		super(aProcessEngine);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ProcessInstance startProcessInstance(WaggonList waggonList) {
		return ProcessHelper.startProcessInstanceByMessage(getProcessEngine(),
				getProcessDefinitionKey(),
				ProcessConstants.Trainpartment.TrainStation.MSG.MSG_DEPARTURE_ORDERED,
				HashMapBuilder.create().withValuePair(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_TRAIN_DEPARTMENT_DATA,
						new TrainDepartureData().addWaggons(waggonList.getWaggonList())).build(),
				null);
	}
	
	public TrainDepartmentNewRunner assumeAllWaggonDamages(int assumdRepairHours) {
		List<WaggonDamageRepairAssumption> generatedRepairAssumptions = generateRepairAssumptions(assumdRepairHours);
		for (WaggonDamageRepairAssumption waggonDamageRepairAssumption : generatedRepairAssumptions) {
			assumeWaggonDamages(waggonDamageRepairAssumption);	
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public void assumeWaggonDamages(WaggonDamageRepairAssumption... waggonRepairAssumption) {
		TaskService taskService = getProcessEngine().getTaskService();
		String additionalValue = null;
		for (WaggonDamageRepairAssumption repairAssumption : waggonRepairAssumption) {
			// get assume task for waggon damage info...
			additionalValue = ((ProcessIdentifier) repairAssumption).generateProcessIdentifier();
			String facilityBusinessKey = BusinessKeyMatcher
					.forProcessDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.DEF.DEF_REPAIR_FACILITY_PROCESS)
					.withAdditionalValue(additionalValue).withParentBusinessKey(getProcessInstance().getBusinessKey())
					.singleResult(getProcessEngine().getRuntimeService()).getBusinessKey();
			taskService.complete(taskService.createTaskQuery().processInstanceBusinessKey(facilityBusinessKey)
					.taskDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.TASK.TASK_ASSUME_WAGGON).singleResult().getId(),
					HashMapBuilder.create()
							.withValuePair(ProcessConstants.Trainpartment.RepairFacility.VAR.VAR_WAGGON_ASSUMPTION_RESULT, repairAssumption)
							.build());
		}
	}

	public TrainDepartmentNewRunner processRollout(boolean rollout) {
		executeAndAssertSingleTask(getProcessEngine(), getProcessInstance(),
				ProcessConstants.Trainpartment.TrainStation.USERTASK.TASK_PROCESS_ROLLOUT, null, true);
		return this;
	}

	public String getBusinessKey() {
		return getProcessInstance().getBusinessKey();
	}
	
	@Override
	public String getProcessDefinitionKey() {
		return ProcessConstants.Trainpartment.TrainStation.DEF.DEF_TRAIN_STATION_PROCESS;
	}
	
	private List<WaggonDamageRepairAssumption> generateRepairAssumptions(int assumdRepairHours) {
		List<WaggonDamageRepairAssumption> result = new ArrayList<WaggonDamageRepairAssumption>();
		WaggonDamageRepairAssumption assumption = null;
		for (Waggon waggon : getProcessInputData().getWaggonList()) {
			for (WaggonDamageInfo waggonDamageInfo : waggon.getCritialDamages()) {
				assumption = WaggonDamageRepairAssumption.fromValues(waggon.getWaggonNumber(),
						waggonDamageInfo.getWaggonDamage().getDamageIdentifier(), waggonDamageInfo.getWaggonErrorCode(), 0);
				assumption.setAssumedRepairDurationInHours(assumdRepairHours);
				result.add(assumption);
			}
		}
		return result;
	}
}