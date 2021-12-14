package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.Severity;

public interface SeverityRepository extends JpaRepository<Severity, Long> {

	public Severity findBySeverityNameAndProject(String severityName, Project project);

	public Severity findBySeverityNameContainsIgnoreCaseAndProject(String severityName, Project project);

	public List<Severity> findBySeverityColorAndProject(String severityColor, Project project);

	public boolean existsBySeverityName(String severityName);

	public boolean existsBySeverityColor(String severityColor);

	public boolean existsBySeverityId(Long severityId);

	public boolean existsByProject(Project project);

	public List<Severity> findByProject(Project project);

	public List<Severity> findBySeverityLevelStartsWithIgnoreCase(String severityLevel);

	public List<Severity> findBySeverityColorStartsWithIgnoreCase(String severityColor);

	public List<Severity> findBySeverityNameStartsWithIgnoreCase(String severityName);

}
