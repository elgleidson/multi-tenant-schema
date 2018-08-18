package com.github.elgleidson.multi.tenant.schema.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantSchemaResolver implements CurrentTenantIdentifierResolver {
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		return TenantContextHolder.getCurrentSchema();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}
