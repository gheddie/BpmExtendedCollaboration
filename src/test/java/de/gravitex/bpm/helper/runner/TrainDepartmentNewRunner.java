package de.gravitex.bpm.helper.runner;

import java.util.List;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.entity.traindepartmentnew.TrainDepartureData;
import de.gravitex.bpm.helper.entity.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonDamageRepairAssumption;
import de.gravitex.bpm.helper.runner.base.ProcessRunner;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;
import de.gravitex.bpm.helper.util.businesskey.base.ProcessIdentifier;
import de.gravitex.bpm.helper.util.businesskey.matcher.BusinessKeyMatcher;
import lombok.Data;

@Data
public class TrainDepartmentNewRunner extends ProcessRunner {

	private ProcessInstance processInstance;

	public TrainDepartmentNewRunner(ProcessEngineServices aProcessEngine) {
		super(aProcessEngine);
	}

	@SuppressWarnings("unchecked")
	public TrainDepartmentNewRunner startProcessInstance(List<Waggon> waggonList) {
		processInstance = ProcessHelper.startProcessInstanceByMessage(getProcessEngine(),
				ProcessConstants.Trainpartment.TrainStation.DEF.DEF_TRAIN_STATION_PROCESS,
				ProcessConstants.Trainpartment.TrainStation.MSG.MSG_DEPARTURE_ORDERED,
				HashMapBuilder.create().withValuePair(ProcessConstants.Trainpartment.TrainStation.VAR.VAR_TRAIN_DEPARTMENT_DATA,
						new TrainDepartureData().addWaggons(waggonList)).build(),
				null);
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
					.withAdditionalValue(additionalValue).withParentBusinessKey(processInstance.getBusinessKey())
					.singleResult(getProcessEngine().getRuntimeService()).getBusinessKey();
			taskService.complete(taskService.createTaskQuery().processInstanceBusinessKey(facilityBusinessKey)
					.taskDefinitionKey(ProcessConstants.Trainpartment.RepairFacility.TASK.TASK_ASSUME_WAGGON).singleResult().getId(),
					HashMapBuilder.create()
							.withValuePair(ProcessConstants.Trainpartment.RepairFacility.VAR.VAR_WAGGON_ASSUMPTION_RESULT, repairAssumption)
							.build());
		}
	}

	public void processRollout(boolean rollout) {
		executeAndAssertSingleTask(getProcessEngine(), processInstance,
				ProcessConstants.Trainpartment.TrainStation.USERTASK.TASK_PROCESS_ROLLOUT, null, true);
	}

	public String getBusinessKey() {
		return processInstance.getBusinessKey();
	}
}