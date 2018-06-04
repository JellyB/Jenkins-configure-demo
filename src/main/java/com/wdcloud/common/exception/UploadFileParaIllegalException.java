package com.wdcloud.common.exception;

import com.wdcloud.framework.core.exception.FrameworkException;

public class UploadFileParaIllegalException extends FrameworkException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UploadFileParaIllegalException(){
		super();
	}
	
	public UploadFileParaIllegalException(String message){
		super(message);
	}
	
	public UploadFileParaIllegalException(String message, Throwable cause) {
		super(message,cause);
	}

}
