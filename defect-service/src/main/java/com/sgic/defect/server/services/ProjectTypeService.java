package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.ProjectTypeDto;

public interface ProjectTypeService {
	public void saveProjectType(ProjectTypeDto projectTypeDto);

	public List<ProjectTypeDto> allProjectType(Integer pageNumber, Integer pageSize);

	public void updateprojectType(Long projectTypeId, ProjectTypeDto projectTypeDto);

	public boolean validateDuplicate(ProjectTypeDto projectTypeDto);

	void deleteProjecttype(Long id);

	boolean isExistById(Long id);

	boolean extistdefectTypeIndefecttype(Long id);

}
