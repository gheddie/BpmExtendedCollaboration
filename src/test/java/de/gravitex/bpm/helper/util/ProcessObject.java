package de.gravitex.bpm.helper.util;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProcessObject implements Serializable {

	private static final long serialVersionUID = -4897705611954853624L;
	
	public boolean checkFor1() {
		return true;
	}
	
	public boolean checkFor2() {
		return true;
	}
	
	public boolean checkFor3() {
		return false;
	}
}