package com.sgic.defect.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.ProjectType;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {

	public boolean existsByProjectTypeName(String projectTypeName);

	public boolean existsByProjectTypeId(Long projectTypeId);

	public boolean existsByProjectTypeNameIgnoreCase(String projectTypeName);

	public ProjectType findByProjectTypeNameIgnoreCase(String projectTypeName);

}
