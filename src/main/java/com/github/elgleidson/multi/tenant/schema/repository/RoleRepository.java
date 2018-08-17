package com.github.elgleidson.multi.tenant.schema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.elgleidson.multi.tenant.schema.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String nome);
	
	boolean existsByName(String nome);
	
}
