package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.ProjectAllocationDto;
import com.sgic.defect.server.dto.ProjectAllocationIdDto;
import com.sgic.defect.server.dto.ProjectAllocationPercentageDto;
import com.sgic.defect.server.entities.Employee;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.ProjectAllocation;
import com.sgic.defect.server.repositories.EmployeeRepository;
import com.sgic.defect.server.repositories.ProjectAllocationRepository;
import com.sgic.defect.server.repositories.ProjectRepository;

@Service
public class ProjectAllocationServiceImpl implements ProjectAllocationService {
	@Autowired
	ProjectAllocationRepository projectAllocationRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	ProjectRepository projectRepository;
	public static final Logger logger = LoggerFactory.getLogger(ProjectAllocationServiceImpl.class);

	@Override
	public void saveProjectAllocation(ProjectAllocationDto projectAllocationDto) {
		logger.info("projectAllocation save Start");
		ProjectAllocation projectAllocation = new ProjectAllocation();
		projectAllocation.setFullName(projectAllocationDto.getFullName());
		projectAllocation.setPercentage(projectAllocationDto.getPercentage());
		projectAllocation.setRoleId(projectAllocationDto.getRoleId());
		projectAllocation.setRoleName(projectAllocationDto.getRoleName());
		Employee employee = employeeRepository.getOne(projectAllocationDto.getEmployeeId());
		projectAllocation.setEmployee(employee);
		Project project = projectRepository.getOne(projectAllocationDto.getProjectId());
		projectAllocation.setProject(project);
		projectAllocationRepository.save(projectAllocation);

	}

	@Override
	public void updateProjectAllocation(Long projectAllocationId, ProjectAllocationDto projectAllocationDto) {
		logger.info("projectAllocation update Start", projectAllocationId);
		ProjectAllocation projectAllocation = projectAllocationRepository.findById(projectAllocationId).get();
		projectAllocation.setFullName(projectAllocationDto.getFullName());
		projectAllocation.setPercentage(projectAllocationDto.getPercentage());
		projectAllocation.setRoleId(projectAllocationDto.getRoleId());
		projectAllocation.setRoleName(projectAllocationDto.getRoleName());
		projectAllocationRepository.save(projectAllocation);

	}

	@Override
	public List<ProjectAllocationIdDto> findAllProjectAllocation(Integer pageNumber, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<ProjectAllocation> pageResult = projectAllocationRepository.findAll(paging);
		List<ProjectAllocation> projectAllocations = pageResult.getContent();
		logger.info("projectAllocation getAll Start");
		return projectAllocations.stream().map(this::convertToProjectAllocationIdDto).collect(Collectors.toList());
	}

	private ProjectAllocationIdDto convertToProjectAllocationIdDto(ProjectAllocation projectAllocation) {
		ProjectAllocationIdDto projectAllocationIdDto = new ProjectAllocationIdDto();
		projectAllocationIdDto.setFullName(projectAllocation.getFullName());
		projectAllocationIdDto.setPercentage(projectAllocation.getPercentage());
		projectAllocationIdDto.setRoleId(projectAllocation.getRoleId());
		projectAllocationIdDto.setRoleName(projectAllocation.getRoleName());
		return projectAllocationIdDto;
	}

	@Override
	public ProjectAllocationIdDto findProjectAllocationById(Long projectAllocationId) {
		logger.info("projectAllocation findById Start", projectAllocationId);
		ProjectAllocation projectAllocation = projectAllocationRepository.findById(projectAllocationId).get();
		ProjectAllocationIdDto projectAllocationIdDto = new ProjectAllocationIdDto();
		projectAllocationIdDto.setFullName(projectAllocation.getFullName());
		projectAllocationIdDto.setPercentage(projectAllocation.getPercentage());
		projectAllocationIdDto.setRoleId(projectAllocation.getRoleId());
		projectAllocationIdDto.setRoleName(projectAllocation.getRoleName());
		return projectAllocationIdDto;
	}

	private ProjectAllocation convert(ProjectAllocation proAllo) {
		ProjectAllocation projectAllocation = new ProjectAllocation();
		projectAllocation.setFullName(proAllo.getFullName());
		return projectAllocation;
	}

	@Override
	public boolean isProjectAllocationfullNameAndProjectIdValidate(String fullName, Long projectId) {
		logger.info("full name validation start");
		List<ProjectAllocation> projectAllocationlist = projectAllocationRepository
				.findByfullNameAndProject(fullName, projectRepository.findById(projectId).get()).stream()
				.map(this::convert).collect(Collectors.toList());
		logger.info("full name validation start");
		if (projectAllocationlist.isEmpty()) {
			logger.info("full name validation start");
			return true;
		}
		return false;
	}

	@Override
	public List<ProjectAllocationDto> getAllProjectAllocationByProjectId(Long projectId) {

		Project project = new Project();
		BeanUtils.copyProperties(projectRepository.findById(projectId).get(), project);
		return projectAllocationRepository.findByProject(project).stream().map(this::convertToProjectAllocationDto)
				.collect(Collectors.toList());

	}

	private ProjectAllocationDto convertToProjectAllocationDto(ProjectAllocation projectAllocation) {
		ProjectAllocationDto projectAllocationDto = new ProjectAllocationDto();
		projectAllocationDto.setFullName(projectAllocation.getFullName());
		projectAllocationDto.setPercentage(projectAllocation.getPercentage());
		projectAllocationDto.setRoleId(projectAllocation.getRoleId());
		projectAllocationDto.setRoleName(projectAllocation.getRoleName());
		if (!(projectAllocation.getProject() == null)) {
			projectAllocationDto.setProjectId(projectAllocation.getProject().getProjectId());
		}
		return projectAllocationDto;
	}

	@Override
	public boolean isProjectAllocationValidbyProjectId(Long projectId) {
		if (projectAllocationRepository.existsById(projectId)) {
			return true;
		}
		return false;
	}

	@Override
	public Long getAllAllocationPercentageByEmployeeId(Long employeeId) {
		Employee employee = employeeRepository.getOne(employeeId);
		List<ProjectAllocationPercentageDto> list = projectAllocationRepository.findByEmployee(employee).stream()
				.map(this::convertToProjectAllocationPercentageDto).collect(Collectors.toList());

		Long percentages = (long) 00;

		for (ProjectAllocationPercentageDto li : list) {
			percentages = percentages + li.getPercentage();
		}

		logger.info("percentage" + percentages);

		return percentages;

	}

	public ProjectAllocationPercentageDto convertToProjectAllocationPercentageDto(ProjectAllocation projectAllocation) {
		ProjectAllocationPercentageDto projectAllocationPercentageDto = new ProjectAllocationPercentageDto();
		projectAllocationPercentageDto.setPercentage(projectAllocation.getPercentage());
		return projectAllocationPercentageDto;
	}

	@Override
	public boolean isProjectAllocationPercentageValidation(Long employeeId, Long percentage) {
		logger.info("percentage validation");
		Long currentPercentage = getAllAllocationPercentageByEmployeeId(employeeId);
		Long availability = 100 - currentPercentage;
		if (availability >= percentage) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteProjectAllocation(Long projectAllocationId) {
		logger.info("projectAllocation delete");
		projectAllocationRepository.deleteById(projectAllocationId);

	}

	@Override
	public boolean isFindProjectAllocationByIdValidate(Long projectAllocationId) {
		if (projectAllocationRepository.existsByProjectAllocationId(projectAllocationId)) {
			return true;
		}
		return false;
	}

	public List<ProjectAllocationDto> projectAllocationSearch(Long employeeId, String fullName, String roleName,
			Long percentage, String projectName) {

		logger.info("project allocation search invoked in implementation");
		Employee employee = new Employee();
		if (employeeId != null) {
			logger.info("Employee ID not null");
			return projectAllocationRepository.findByEmployee(employee).stream()
					.map(this::convertToProjectAllocationDto).collect(Collectors.toList());
		} else if (!fullName.isEmpty()) {
			logger.info("full name not blank");
			return projectAllocationRepository.findByFullNameContainsIgnoreCase(fullName).stream()
					.map(this::convertToProjectAllocationDto).collect(Collectors.toList());
		} else if (!roleName.isEmpty()) {
			logger.info("Role name not null");
			return projectAllocationRepository.findByRoleName(roleName).stream()
					.map(this::convertToProjectAllocationDto).collect(Collectors.toList());
		} else if (percentage != null) {
			logger.info("Percentage not null");
			return projectAllocationRepository.findByPercentage(percentage).stream()
					.map(this::convertToProjectAllocationDto).collect(Collectors.toList());
		} else if (!projectName.isEmpty()) {
			logger.info("project name not blank");
			return projectAllocationRepository.findByProject(projectRepository.findByProjectNameIgnoreCase(projectName))
					.stream().map(this::convertToProjectAllocationDto).collect(Collectors.toList());
		}
		logger.info("All searching paramenters are null");

		return projectAllocationRepository.findAll().stream().map(this::convertToProjectAllocationDto)
				.collect(Collectors.toList());
	}

	@Override
	public boolean isProjectAllocationValidbyEmployeeId(Long employeeId) {
		if(employeeRepository.existsById(employeeId))
		{
			return true;
		}
		return false;
	}
}
