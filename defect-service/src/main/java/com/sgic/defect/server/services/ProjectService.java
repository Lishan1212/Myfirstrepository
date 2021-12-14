package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.ProjectDto;
import com.sgic.defect.server.dto.ProjectFindAllDto;
import com.sgic.defect.server.dto.ProjectNameDto;

public interface ProjectService {

	public void saveProject(ProjectDto projectDto);

	public List<ProjectFindAllDto> findAllProject(int pageNumber, int pageSize);

	public List<ProjectFindAllDto> projectSort(String direction, String property);

	public void updateProject(Long Id, ProjectDto ProjectDto);

	public List<ProjectNameDto> getProjectName();

	public ProjectFindAllDto findProjectById(Long id);

	public boolean validateProjectName(String projectName);

	public boolean validateProjectId(Long id);

	public boolean validateProjectTypeID(Long id);

	public List<ProjectDto> findAllProject(String projectName, String clientAddress, String clientName,
			String statusName, String projectType, String prefix, String description, String contactPersonName,
			String contactPersonMobileNumber, String contactPersonEmailId, String startDate, String endDate,
			Integer pageNumber, String property, String direction);

	public void deleteProject(Long id);

	public boolean validateForeignKeyProject(Long id);

}