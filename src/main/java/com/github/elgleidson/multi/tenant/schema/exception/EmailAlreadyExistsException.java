package com.github.elgleidson.multi.tenant.schema.exception;

public class EmailAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException(String email) {
		super(email);
	}

}
