package com.kynsof.scheduled.infrastructure.command;

import com.kynsof.scheduled.infrastructure.entity.Services;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceWriteDataJPARepository extends JpaRepository<Services, UUID> {
}
