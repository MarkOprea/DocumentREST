package com.document.domain;

public class DocumentException  extends RuntimeException
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3683248625914771431L;

	public DocumentException(String message) {
	        super(message);
	    }

	    public DocumentException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
