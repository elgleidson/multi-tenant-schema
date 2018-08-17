package com.github.elgleidson.multi.tenant.schema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.elgleidson.multi.tenant.schema.domain.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

	Optional<Tenant> findByName(String name);
	
	Optional<Tenant> findBySchema(String schema);
	
}
