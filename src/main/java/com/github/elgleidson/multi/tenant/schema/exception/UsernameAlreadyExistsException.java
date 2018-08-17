package com.github.elgleidson.multi.tenant.schema.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UsernameAlreadyExistsException(String username) {
		super(username);
	}

}
