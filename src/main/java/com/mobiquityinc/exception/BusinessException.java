package com.mobiquityinc.exception;

public class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String message, Exception e) {
		super(message, e);
	}

	public BusinessException(String message) {
		super(message);
	}
}
