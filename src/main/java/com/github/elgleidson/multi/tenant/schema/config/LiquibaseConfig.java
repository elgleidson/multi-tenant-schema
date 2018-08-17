package com.github.elgleidson.multi.tenant.schema.config;

import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.elgleidson.multi.tenant.schema.domain.Tenant;
import com.github.elgleidson.multi.tenant.schema.repository.TenantRepository;

import liquibase.integration.spring.MultiTenantSpringLiquibase;
import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquibaseConfig {
	
	@Bean
	public SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:/db/changelog/db.changelog-core.xml");
		liquibase.setDefaultSchema("core");
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
