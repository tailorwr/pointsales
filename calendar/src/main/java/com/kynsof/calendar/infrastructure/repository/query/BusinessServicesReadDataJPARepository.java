package com.kynsof.calendar.infrastructure.repository.query;

import com.kynsof.calendar.infrastructure.entity.BusinessServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BusinessServicesReadDataJPARepository extends JpaRepository<BusinessServices, UUID>, JpaSpecificationExecutor<BusinessServices> {
    Page<BusinessServices> findAll(Specification specification, Pageable pageable);
}
