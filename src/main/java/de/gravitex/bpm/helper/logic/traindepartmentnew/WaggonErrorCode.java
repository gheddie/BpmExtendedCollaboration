package de.gravitex.bpm.helper.logic.traindepartmentnew;

public enum WaggonErrorCode {
	C1(true), C2(true), N1(false), C3(true), C4(true);
	
	private boolean critical;
	
	WaggonErrorCode(boolean aCritical) {
		this.critical = aCritical;
	}

	public boolean isCritical() {
		return critical;
	}
}