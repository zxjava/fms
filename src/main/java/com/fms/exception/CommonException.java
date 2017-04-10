package com.fms.exception;

public class CommonException extends RuntimeException {


	private static final long serialVersionUID = 7608330395582067150L;

	/**
	 * @param message
	 *            原异常信息
	 */
	public CommonException(String message) {
		super(message);
	}

	/**
	 * @param message
	 *            异常信息
	 */
	public CommonException(String message, Exception e) {
		super(message, e);
	}
	
}
