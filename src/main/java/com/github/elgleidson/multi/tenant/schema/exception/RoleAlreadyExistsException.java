package com.github.elgleidson.multi.tenant.schema.exception;

public class RoleAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RoleAlreadyExistsException(String nome) {
		super(nome);
	}
	
}
