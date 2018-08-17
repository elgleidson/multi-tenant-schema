package com.github.elgleidson.multi.tenant.schema.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.github.elgleidson.multi.tenant.schema.domain.Demo;

@Repository
public interface DemoRepository extends PagingAndSortingRepository<Demo, Long>, JpaSpecificationExecutor<Demo> {

}
