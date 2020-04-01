package de.gravitex.bpm.helper.delegate.traindepartmentnew;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpm.helper.constant.ProcessConstants;
import de.gravitex.bpm.helper.delegate.base.ExtendedJavaDelegate;

public class CancelDepartingOrderDelegate extends ExtendedJavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.getProcessEngine().getRuntimeService()
				.startProcessInstanceByKey(ProcessConstants.Trainpartment.RestartDepartingOrder.DEF.DEF_RESTART_DEP_ORDERS_PROCESS);
	}
}