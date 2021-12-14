package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.controller.PriorityController;
import com.sgic.defect.server.dto.ProjectTypeDto;
import com.sgic.defect.server.entities.ProjectType;
import com.sgic.defect.server.repositories.ProjectRepository;
import com.sgic.defect.server.repositories.ProjectTypeRepository;

@Service
public class ProjectTypeSericeimpl implements ProjectTypeService {
	@Autowired
	ProjectTypeRepository projectTypeRepository;

	@Autowired
	ProjectRepository projectRepository;

	private final Logger logger = LoggerFactory.getLogger(ProjectTypeSericeimpl.class);

	public void saveProjectType(ProjectTypeDto projectTypeDto) {

		logger.info("Save Started Sucessfully");
		ProjectType projectType = new ProjectType();
		projectType.setProjectTypeName(projectTypeDto.getProjectTypeName());
		projectTypeRepository.save(projectType);
		logger.info("Project Save Sucessfully");

	}

	@Override
	public List<ProjectTypeDto> allProjectType(Integer pageNumber, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<ProjectType> pageResult = projectTypeRepository.findAll(paging);
		List<ProjectType> defecttypes = pageResult.getContent();
		return defecttypes.stream().map(this::convertProjectTypeDto).collect(Collectors.toList());
	}

	private ProjectTypeDto convertProjectTypeDto(ProjectType projectType) {
		ProjectTypeDto projectTypeDto = new ProjectTypeDto();
		projectTypeDto.setProjectTypeName(projectType.getProjectTypeName());
		return projectTypeDto;

	}

	@Override
	public void updateprojectType(Long projectTypeId, ProjectTypeDto projectTypeDto) {
		logger.info("updaate Started...");
		ProjectType projectType = new ProjectType();
		projectType.setProjectTypeName(projectTypeDto.getProjectTypeName());
		projectTypeRepository.save(projectType);
		logger.info("updated Sucessfully");
	}

	private ProjectType convert(ProjectType projecttype) {
		ProjectType projectType = new ProjectType();
		projectType.setProjectTypeName(projecttype.getProjectTypeName());
		return projectType;
	}

	@Override

	public boolean validateDuplicate(ProjectTypeDto projectTypeDto) {
		logger.info("validate type");

		if (projectTypeRepository.existsByProjectTypeName(projectTypeDto.getProjectTypeName())) {

			return true;
		}

		return false;
	}

	@Override
	public void deleteProjecttype(Long id) {
		logger.info("delete the priority");
		projectTypeRepository.deleteById(id);
	}

	@Override
	public boolean extistdefectTypeIndefecttype(Long id) {
		logger.info("Check the Depency");
		ProjectType projectType = projectTypeRepository.findById(id).get();
		if (projectRepository.existsByProjectType(projectType)) {
			return true;
		}
		return false;

	}

	@Override
	public boolean isExistById(Long id) {
		if (projectRepository.existsById(id)) {
			return true;
		}
		return true;
	}

}
