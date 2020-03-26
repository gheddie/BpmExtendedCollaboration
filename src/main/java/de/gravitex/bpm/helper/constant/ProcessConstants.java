package de.gravitex.bpm.helper.constant;

public class ProcessConstants {
	
	public class Main {
		
		public class TASK {
			public static final String TASK_M2 = "TASK_M2";
			public static final String TASK_M1 = "TASK_M1";
			public static final String TASK_M4 = "TASK_M4";
		}	
		
		public class MSG {
			public static final String MSG_RECALL = "MSG_RECALL";
		}
		
		public class DEF {
			public static final String MAIN_PROCESS = "MAIN_PROCESS";		
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
			public static final String SLAVE_PROCESS = "SLAVE_PROCESS";			
		}
	}
}