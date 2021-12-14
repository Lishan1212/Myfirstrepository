package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Employee;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.ProjectAllocation;

public interface ProjectAllocationRepository extends JpaRepository<ProjectAllocation, Long> {
	public List<ProjectAllocation> findByfullNameAndProject(String fullName, Project project);

	public List<ProjectAllocation> findByProject(Project project);

	public List<ProjectAllocation> findByEmployee(Employee employee);

	public boolean existsByEmployee(Long employeeId);

	public boolean existsByfullName(String fullName);

	public boolean existsByPercentage(Long employeeId);

	public List<ProjectAllocation> findByRoleName(String roleName);

	public List<ProjectAllocation> findByFullNameContainsIgnoreCase(String fullName);

	public List<ProjectAllocation> findByPercentage(Long percentage);

	public boolean existsByProject(Project project);

	public boolean existsByProjectAllocationId(Long projectAllocationId);

}
