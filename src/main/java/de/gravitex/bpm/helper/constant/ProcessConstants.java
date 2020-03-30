package de.gravitex.bpm.helper.constant;

import de.gravitex.bpm.helper.delegate.traindepartmentnew.OrderWaggonRepairsDelegate;
import de.gravitex.bpm.helper.logic.traindepartmentnew.TrainDepartmentData;
import de.gravitex.bpm.helper.logic.traindepartmentnew.Waggon;
import de.gravitex.bpm.helper.logic.traindepartmentnew.WaggonRepairAssumption;

public class ProcessConstants {

	public class Trainpartment {

		public class RepairFacility {

			public class TASK {
				public static final String TASK_ASSUME_WAGGON = "TASK_ASSUME_WAGGON";
			}

			public class DEF {
				public static final String DEF_REPAIR_FACILITY_PROCESS = "DEF_REPAIR_FACILITY_PROCESS";
			}

			public class MSG {
				public static final String MSG_START_ASSUMPTION = "MSG_START_ASSUMPTION";
			}

			public class VAR {
				/**
				 * Type:
				 * ({@link Integer})
				 * Description:
				 * Das Ergebnis einer Reparatur-Abschätzung (erfolgt per {@link WaggonRepairAssumption}).
				 */
				public static final String VAR_WAGGON_ASSUMPTION_RESULT = "VAR_WAGGON_ASSUMPTION_RESULT";
			}
		}

		public class TrainStation {
			
			public class ERROR {
				public static final String ERR_INVALID_WAGGON_ASSUMPTION = "ERR_INVALID_WAGGON_ASSUMPTION";
			}

			public class MSG {
				public static final String MSG_DEPARTURE_ORDERED = "MSG_DEPARTURE_ORDERED";
				public static final String MSG_WAGGON_ASSUMED = "MSG_WAGGON_ASSUMED";
			}

			public class DEF {
				public static final String DEF_TRAIN_STATION_PROCESS = "DEF_TRAIN_STATION_PROCESS";
			}

			public class VAR {
				/**
				 * Type:
				 * ({@linkplain TrainDepartmentData})
				 * Description: Process backbone
				 * containing the department waggons. Passed to
				 * {@link ProcessConstants.Trainpartment.TrainStation.DEF#DEF_TRAIN_STATION_PROCESS}
				 * on process start up.
				 */
				public static final String VAR_TRAIN_DEPARTMENT_DATA = "VAR_TRAIN_DEPARTMENT_DATA";
				/**
				 * Type:
				 * ({@link Waggon})
				 * Description:
				 * Looped over by
				 * {@link OrderWaggonRepairsDelegate}. Passed to
				 * {@link ProcessConstants.Trainpartment.RepairFacility.DEF#DEF_REPAIR_FACILITY_PROCESS}
				 * by message {@link ProcessConstants.Trainpartment.RepairFacility.MSG#MSG_START_ASSUMPTION}.
				 * Also passes back an assumed waggon to the main process.
				 */
				public static final String VAR_SINGLE_WAGGON_TO_ASSUME = "VAR_SINGLE_WAGGON_TO_ASSUME";
			}
		}
	}

	public class Collaboration {

		public class Main {

			public class GATEWAY {
				public static final String GW_MASTER_1 = "GW_MASTER_1";
				public static final String GW_MASTER_2 = "GW_MASTER_2";
			}

			public class TASK {
				public static final String TASK_M2 = "TASK_M2";
				public static final String TASK_M1 = "TASK_M1";
				public static final String TASK_M4 = "TASK_M4";
				public static final String TASK_M0 = "TASK_M0";
				public static final String TASK_M5 = "TASK_M5";
				public static final String TASK_M7 = "TASK_M7";
			}

			public class MSG {
				public static final String MSG_RECALL_M5 = "MSG_RECALL_M5";
			}

			public class DEF {
				public static final String DEF_MAIN_PROCESS = "DEF_MAIN_PROCESS";
			}

			public class VAR {
				public static final String VAR_MAINVAL = "VAR_MAINVAL";
				public static final String VAR_PROCESS_DATA = "VAR_PROCESS_DATA";
				// a single process item passed to 'another slave' process ('M6')
				public static final String VAR_PROCESS_DATA_ITEM = "VAR_PROCESS_DATA_ITEM";
			}
		}

		public class Slave {

			public class GATEWAY {
				public static final String GW_SLAVE_1 = "GW_SLAVE_1";
				public static final String GW_SLAVE_2 = "GW_SLAVE_2";
			}

			public class TASK {
				public static final String TASK_S0 = "TASK_S0";
				public static final String TASK_S1 = "TASK_S1";
				public static final String TASK_S3 = "TASK_S3";
				public static final String TASK_S5 = "TASK_S5";
				public static final String TASK_S6 = "TASK_S6";
			}

			public class MSG {
				public static final String MSG_CALL_A = "MSG_CALL_A";
				public static final String MSG_CALL_B = "MSG_CALL_B";
				public static final String MSG_CALL_S5 = "MSG_CALL_S5";
				public static final String MSG_CALL_S6 = "MSG_CALL_S6";
			}

			public class CALLBACK {
				public static final String CALLBACK_S4 = "CALLBACK_S4";
			}

			public class DEF {
				public static final String DEF_SLAVE_PROCESS = "DEF_SLAVE_PROCESS";
			}

			public class VAR {
				public static final String VAR_SUBVAL = "VAR_SUBVAL";
			}
		}

		public class AnotherSlave {

			public class MSG {
				public static final String MSG_START_ANOTHER_SLAVE = "MSG_START_ANOTHER_SLAVE";
				public static final String MSG_FINISH_AS = "MSG_FINISH_AS";
			}

			public class DEF {
				public static final String DEF_ANOTHER_SLAVE_PROCESS = "DEF_ANOTHER_SLAVE_PROCESS";
			}

			public class VAR {
				public static final String VAR_ANOTHER_SLAVE_ITEM = "VAR_ANOTHER_SLAVE_ITEM";
			}
		}
	}

	public class Common {

		public class VAR {
			public static final String VAR_MASTER_PROCESS_BK = "VAR_MASTER_PROCESS_BK";
		}
	}
}