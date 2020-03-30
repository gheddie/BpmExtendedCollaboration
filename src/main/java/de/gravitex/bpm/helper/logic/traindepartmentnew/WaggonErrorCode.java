package de.gravitex.bpm.helper.logic.traindepartmentnew;

public enum WaggonErrorCode {
	C1(true), C2(true), N1(false);
	
	private boolean critical;
	
	WaggonErrorCode(boolean aCritical) {
		this.critical = aCritical;
	}

	public boolean isCritical() {
		return critical;
	}
}