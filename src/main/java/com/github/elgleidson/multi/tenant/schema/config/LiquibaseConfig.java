package com.github.elgleidson.multi.tenant.schema.config;

import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.github.elgleidson.multi.tenant.schema.multitenant.TenantContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.elgleidson.multi.tenant.schema.domain.core.Tenant;
import com.github.elgleidson.multi.tenant.schema.repository.core.TenantRepository;

import liquibase.integration.spring.MultiTenantSpringLiquibase;
import liquibase.integration.spring.SpringLiquibase;

@Component
public class LiquibaseConfig {
	
	@Bean
	public SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:/db/changelog/db.changelog-core.xml");
		liquibase.setDefaultSchema(TenantContextHolder.DEFAULT_SCHEMA);
		liquibase.setShouldRun(true);
		
		return liquibase;
	}
	
	@Bean
	public MultiTenantSpringLiquibase liquibaseMultiTenant(DataSource dataSource, TenantRepository tenantRepository) {
		MultiTenantSpringLiquibase liquibase = new MultiTenantSpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:/db/changelog/db.changelog-tenants.xml");
		List<String> schemas = tenantRepository.findAll().stream().map(Tenant::getSchema).collect(Collectors.toList());
		liquibase.setSchemas(schemas);
		liquibase.setShouldRun(true);			
		
		return liquibase;
	}

}
