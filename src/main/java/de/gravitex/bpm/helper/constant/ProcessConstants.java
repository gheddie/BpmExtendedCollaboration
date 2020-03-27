package de.gravitex.bpm.helper.constant;

public class ProcessConstants {
	
	public class Main {
		
		public class TASK {
			public static final String TASK_M2 = "TASK_M2";
			public static final String TASK_M1 = "TASK_M1";
			public static final String TASK_M4 = "TASK_M4";
			public static final String TASK_M0 = "TASK_M0";
		}	
		
		public class MSG {
			public static final String MSG_RECALL = "MSG_RECALL";
		}
		
		public class DEF {
			public static final String DEF_MAIN_PROCESS = "DEF_MAIN_PROCESS";		
		}

		public class VAR {
			public static final String VAR_MAINVAL = "VAR_MAINVAL";
		}
	}
	
	public class Slave {
		
		public class TASK {
			public static final String TASK_S1 = "TASK_S1";
			public static final String TASK_S3 = "TASK_S3";
		}
		
		public class MSG {
			public static final String MSG_CALL = "MSG_CALL";		
		}
		
		public class CALLBACK {
			public static final String CALLBACK_MSG_RECALL = "CALLBACK_MSG_RECALL";
		}
		
		public class DEF {
			public static final String DEF_SLAVE_PROCESS = "DEF_SLAVE_PROCESS";			
		}
		
		public class VAR {
			public static final String VAR_SUBVAL = "VAR_SUBVAL";
		}
	}
}