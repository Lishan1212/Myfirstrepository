package com.sgic.defect.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.EmailPoint;

public interface EmailPointRepository extends JpaRepository<EmailPoint, Long> {
	boolean existsByIsActive(boolean isActive);

	boolean existsByPointId(Long pointId);

}
