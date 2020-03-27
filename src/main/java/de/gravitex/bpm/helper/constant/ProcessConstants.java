package de.gravitex.bpm.helper.constant;

public class ProcessConstants {
	
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
		}	
		
		public class MSG {
			public static final String MSG_RECALL_M5 = "MSG_RECALL_M5";
		}
		
		public class DEF {
			public static final String DEF_MAIN_PROCESS = "DEF_MAIN_PROCESS";		
		}

		public class VAR {
			public static final String VAR_MAINVAL = "VAR_MAINVAL";
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
}