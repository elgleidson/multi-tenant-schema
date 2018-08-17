package com.github.elgleidson.multi.tenant.schema.exception;

public class RoleNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RoleNotExistsException(String nome) {
		super(nome);
	}
	
}
