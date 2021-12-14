package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.ProjectType;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	public boolean existsByProjectId(Long projectId);

	public Page<Project> findAll(Pageable page);

	public boolean existsByProjectName(String projectName);

	public List<Project> findByProjectNameStartsWithIgnoreCase(String projectName, Pageable page);

	public List<Project> findByClientAddressContainsIgnoreCase(String clientAddress, Pageable page);

	public List<Project> findByClientNameStartsWithIgnoreCase(String clientName, Pageable page);

	public List<Project> findByStatusNameStartsWithIgnoreCase(String statusName, Pageable page);

	public List<Project> findByStartDate(String startDate, Pageable page);

	public List<Project> findByProjectType(Long projectType, Pageable page);

	public List<Project> findByPrefixStartsWithIgnoreCase(String prefix, Pageable page);

	public boolean existsByProjectType(ProjectType projectType);

	public List<Project> findByEndDate(String endDate, Pageable page);

	public List<Project> findByDescriptionContainsIgnoreCase(String description, Pageable page);

	public List<Project> findByContactPersonNameStartsWithIgnoreCase(String contactPersonName, Pageable page);

	public List<Project> findAll(Sort sort);

//	public Object findByProjectId(String projectId);

	public List<Project> findByContactPersonMobileNumberStartsWith(String contactPersonMobileNumber, Pageable page);

	public List<Project> findByContactPersonEmailIdStartsWithIgnoreCase(String contactPersonEmailId, Pageable page);

	public Project findByProjectNameIgnoreCase(String employeeName);


}
