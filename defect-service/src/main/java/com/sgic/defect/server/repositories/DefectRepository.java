package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Defect;
import com.sgic.defect.server.entities.DefectStatus;
import com.sgic.defect.server.entities.DefectType;
import com.sgic.defect.server.entities.Priority;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.Releases;
import com.sgic.defect.server.entities.Severity;
import com.sgic.defect.server.entities.Submodule;

public interface DefectRepository extends JpaRepository<Defect, Long> {

	public boolean existsBySeverity(Severity severity);

	boolean existsByDefectId(Long defectId);

	public boolean existsByPriority(Priority priority);

	boolean existsByDefectStatus(DefectStatus defectStatus);

	boolean existsByDefectType(DefectType defectType);

	List<Defect> findByProject(Project project, Pageable page);

	List<Defect> findByDescriptionContaining(String description);

	List<Defect> findByAssignedUserId(Long assignedUserId, Pageable page);

	List<Defect> findBySeverity(Severity severity, Pageable page);

	List<Defect> findByPriority(Priority priority, Pageable page);

	List<Defect> findBySubmodule(Submodule submodule, Pageable page);

	List<Defect> findByDefectType(DefectType defectType, Pageable page);

	List<Defect> findByReleases(Releases releases, Pageable page);

	List<Defect> findByDefectStatus(DefectStatus defectStatus, Pageable page);

	List<Defect> findByCreativeUserId(Long creativeUserId, Pageable page);

	boolean existsByReleases(Releases releases);

//	public List<Defect> findByProject(Project project);

	public boolean existsByProject(Project project);

}
