package de.gravitex.bpm.helper.delegate.traindepartmentnew;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.util.HashMapBuilder;
import de.gravitex.bpm.helper.util.ProcessHelper;

public class OrderWaggonRepairsDelegate implements JavaDelegate {

	private static final String VAR_SINGLE_EVAL_WAGGON = "VAR_SINGLE_EVAL_WAGGON";

	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Waggon waggon = (Waggon) execution.getVariable(VAR_SINGLE_EVAL_WAGGON);
		if (waggon.isCritical()) {
			ProcessHelper.startProcessInstanceByMessage(execution.getProcessEngine(),
					ProcessConstants.Trainpartment.Evaluation.DEF.DEF_DEPARTMENT_EVALUATION_PROCESS,
					ProcessConstants.Trainpartment.Evaluation.MSG.MSG_START_EVALUATION, HashMapBuilder.create()
							.withValuePair(ProcessConstants.Trainpartment.Evaluation.VAR.VAR_EVALUATION_WAGGON, waggon).build(),
					execution.getBusinessKey());
		}
	}
}