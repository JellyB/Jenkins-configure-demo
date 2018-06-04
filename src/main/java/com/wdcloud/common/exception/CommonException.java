package com.wdcloud.common.exception;

import com.wdcloud.framework.core.exception.FrameworkException;

/**
 * Common Exception
 * @author whd
 * @since 2015-11-10 14:19:10
 */
public class CommonException extends FrameworkException{
	
	private static final long serialVersionUID = 1L;

	public CommonException() {
		super();
	}
	
	public CommonException(String message) {
		super(message);
	}
	
	public CommonException(String message, Throwable cause) {
		super(message,cause);
	}
	
}
