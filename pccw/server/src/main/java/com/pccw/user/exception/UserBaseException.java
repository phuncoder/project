package com.pccw.user.exception;

public abstract class UserBaseException extends Exception {

	public UserBaseException(String message) {
		super(message);
	}

	public String getMessageJSON() {
		return String.format("{\"error\": \"%s\"}", this.getMessage());
		
	}
}
