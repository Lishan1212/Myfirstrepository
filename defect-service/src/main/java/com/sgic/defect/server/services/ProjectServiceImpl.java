package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.ProjectDto;
import com.sgic.defect.server.dto.ProjectFindAllDto;
import com.sgic.defect.server.dto.ProjectNameDto;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.ProjectType;
import com.sgic.defect.server.repositories.DefectRepository;
import com.sgic.defect.server.repositories.DefectStatusRepository;
import com.sgic.defect.server.repositories.DefectTypeRepository;
import com.sgic.defect.server.repositories.ModuleRepository;
import com.sgic.defect.server.repositories.PriorityRepository;
import com.sgic.defect.server.repositories.ProjectAllocationRepository;
import com.sgic.defect.server.repositories.ProjectRepository;
import com.sgic.defect.server.repositories.ProjectTypeRepository;
import com.sgic.defect.server.repositories.ReleasesRepository;
import com.sgic.defect.server.repositories.SeverityRepository;
import com.sgic.defect.server.util.Utills;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectTypeRepository projectTypeRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	DefectRepository defectRepository;
	@Autowired
	DefectStatusRepository defectStatusRepository;
	@Autowired
	DefectTypeRepository defectTypeRepository;
	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	PriorityRepository priorityRepository;
	@Autowired
	ProjectAllocationRepository projectAllocationRepository;
	@Autowired
	ReleasesRepository releasesRepository;
	@Autowired
	SeverityRepository severityRepository;

	private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Override
	public void saveProject(ProjectDto projectDto) {

		logger.info("save project started");
		Project project = new Project();
		project.setPrefix(projectDto.getPrefix());
		ProjectType projectType = projectTypeRepository.getOne(projectDto.getProjectTypeId());
		project.setProjectType(projectType);
		project.setClientAddress(projectDto.getClientAddress());
		project.setClientName(projectDto.getClientName());
		project.setContactPersonEmailId(projectDto.getContactPersonEmailId());
		project.setContactPersonMobileNumber(projectDto.getContactPersonMobileNumber());
		project.setContactPersonName(projectDto.getContactPersonName());
		project.setDescription(projectDto.getDescription());
		project.setProjectName(projectDto.getProjectName());
		project.setStatusName(projectDto.getStatusName());

		if (Utills.dateValidation(projectDto.getStartDate(), projectDto.getEndDate())) {
			logger.info("Date validation started");
			project.setStartDate(projectDto.getStartDate());
			project.setEndDate(projectDto.getEndDate());
			projectRepository.save(project);

		}
		logger.info("Date Validation Failed");

	}

	@Override
	public void updateProject(Long id, ProjectDto projectDto) {
		logger.info("Update Project started");
		Project project = projectRepository.findById(id).get();
		project.setPrefix(projectDto.getPrefix());
		ProjectType projectType = projectTypeRepository.getOne(projectDto.getProjectTypeId());
		project.setProjectType(projectType);
		project.setClientAddress(projectDto.getClientAddress());
		project.setClientName(projectDto.getClientName());
		project.setContactPersonEmailId(projectDto.getContactPersonEmailId());
		project.setContactPersonMobileNumber(projectDto.getContactPersonMobileNumber());
		project.setContactPersonName(projectDto.getContactPersonName());
		project.setDescription(projectDto.getDescription());
		project.setEndDate(projectDto.getEndDate());

		project.setProjectName(projectDto.getProjectName());
		project.setStartDate(projectDto.getStartDate());
		project.setStatusName(projectDto.getStatusName());
		projectRepository.save(project);

	}

	public List<ProjectFindAllDto> findAllProject(int pageNumber, int pageSize) {

		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<Project> pageResult = projectRepository.findAll(paging);
		List<Project> projects = pageResult.getContent();
		logger.info("Find all Project started");

		return projects.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private ProjectFindAllDto convertToDto(Project project) {
		logger.info("convert ProjectDto started ");
		ProjectFindAllDto projectFindAllDto = new ProjectFindAllDto();
		projectFindAllDto.setPrefix(project.getPrefix());

		projectFindAllDto.setProjectTypeId(project.getProjectType().getProjectTypeId());

		projectFindAllDto.setClientAddress(project.getClientAddress());
		projectFindAllDto.setClientName(project.getClientName());
		projectFindAllDto.setContactPersonEmailId(project.getContactPersonEmailId());
		projectFindAllDto.setContactPersonMobileNumber(project.getContactPersonMobileNumber());
		projectFindAllDto.setContactPersonName(project.getContactPersonName());
		projectFindAllDto.setDescription(project.getDescription());
		projectFindAllDto.setEndDate(project.getEndDate());
		// projectFindAllDto.setProjectId(projectDto.getProjectId());
		projectFindAllDto.setProjectName(project.getProjectName());
		projectFindAllDto.setStartDate(project.getStartDate());
		projectFindAllDto.setStatusName(project.getStatusName());
		return projectFindAllDto;
	}

	@Override
	public List<ProjectNameDto> getProjectName() {
		logger.info("Find Project name started");
		return projectRepository.findAll().stream().map(this::convertNameToDto1).collect(Collectors.toList());
	}

	private ProjectNameDto convertNameToDto1(Project project) {
		logger.info("Conver ProjectNameDto started ");
		ProjectNameDto projectNameDto = new ProjectNameDto();
		projectNameDto.setProjectId(project.getProjectId());
		projectNameDto.setProjectName(project.getProjectName());
		return projectNameDto;
	}

	@Override
	public ProjectFindAllDto findProjectById(Long projectId) {

		logger.info("Find project started");
		if (projectRepository.existsByProjectId(projectId)) {
			Project project = projectRepository.findById(projectId).get();
			return convertToDto(project);
		}
		return null;

	}

	@Override
	public boolean validateProjectName(String projectName) {
		if (!projectRepository.existsByProjectName(projectName)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateProjectId(Long projectId) {
		if (projectRepository.existsByProjectId(projectId)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateProjectTypeID(Long projectTypeId) {
		if (projectTypeRepository.existsByProjectTypeId(projectTypeId)) {
			return true;
		}
		return false;
	}

	public ProjectDto convertProjectDto(Project project) {
		logger.info("convert to ProjectDto started");
		ProjectDto projectDto = new ProjectDto();
		projectDto.setPrefix(project.getPrefix());

		projectDto.setProjectTypeId(project.getProjectType().getProjectTypeId());

		projectDto.setClientAddress(project.getClientAddress());
		projectDto.setClientName(project.getClientName());
		projectDto.setContactPersonEmailId(project.getContactPersonEmailId());
		projectDto.setContactPersonMobileNumber(project.getContactPersonMobileNumber());
		projectDto.setContactPersonName(project.getContactPersonName());
		projectDto.setDescription(project.getDescription());
		projectDto.setEndDate(project.getEndDate());
		// projectDto.setProjectId(project.getProjectId());
		projectDto.setProjectName(project.getProjectName());
		projectDto.setStartDate(project.getStartDate());
		projectDto.setStatusName(project.getStatusName());
		logger.info("convert to ProjectDto returned");
		return projectDto;
	}

	@Override
	public List<ProjectFindAllDto> projectSort(String direction, String property) {
		if (property.equals("projectTypeName")) {
			return projectRepository.findAll(Sort.by(Direction.fromString(direction), "projectType")).stream()
					.map(this::convertToDto).collect(Collectors.toList());

		}

		return projectRepository.findAll(Sort.by(Direction.fromString(direction), property)).stream()
				.map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public void deleteProject(Long id) {
		logger.info("delete project impl started");

		projectRepository.deleteById(id);

	}

	@Override
	public boolean validateForeignKeyProject(Long id) {

		if (defectRepository.existsByProject(projectRepository.findById(id).get())
				|| defectStatusRepository.existsByProject(projectRepository.findById(id).get())
				|| defectTypeRepository.existsByProject(projectRepository.findById(id).get())
				|| moduleRepository.existsByProject(projectRepository.findById(id).get())
				|| priorityRepository.existsByProject(projectRepository.findById(id).get())
				|| projectAllocationRepository.existsByProject(projectRepository.findById(id).get())
				|| releasesRepository.existsByProject(projectRepository.findById(id).get())
				|| severityRepository.existsByProject(projectRepository.findById(id).get())) {

			return false;
		}
		return true;
	}

	@Override
	public List<ProjectDto> findAllProject(String projectName, String clientAddress, String clientName,
			String statusName, String projectType, String prefix, String description, String contactPersonName,
			String contactPersonMobileNumber, String contactPersonEmailId, String startDate, String endDate,
			Integer pageNumber, String property, String direction) {
		if (!projectName.isEmpty()) {
			logger.info("project name value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByProjectNameStartsWithIgnoreCase(projectName, pageable).stream()
					.map(this::convertProjectDto).collect(Collectors.toList());
		}

		else if (!clientAddress.isEmpty()) {
			logger.info("client Address value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByClientAddressContainsIgnoreCase(clientAddress, pageable).stream()
					.map(this::convertProjectDto).collect(Collectors.toList());
		}

		else if (!clientName.isEmpty()) {
			logger.info("client name value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByClientNameStartsWithIgnoreCase(clientName, pageable).stream()
					.map(this::convertProjectDto).collect(Collectors.toList());
		} else if (!statusName.isEmpty()) {
			logger.info("Status name value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByStatusNameStartsWithIgnoreCase(statusName, pageable).stream()
					.map(this::convertProjectDto).collect(Collectors.toList());

		} else if (!(projectType.isEmpty())) {
			logger.info("project type value passed");
			if (projectTypeRepository.existsByProjectTypeName(projectName)) {
				logger.info("project type name validate done");
				ProjectType projectTypeObject = projectTypeRepository.findByProjectTypeNameIgnoreCase(projectType);
				logger.info("project type object received");
				Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
				return projectRepository.findByProjectType(projectTypeObject.getProjectTypeId(), pageable).stream()
						.map(this::convertProjectDto).collect(Collectors.toList());
			}

		}

		else if (!prefix.isEmpty()) {
			logger.info("prefix value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByPrefixStartsWithIgnoreCase(prefix, pageable).stream()
					.map(this::convertProjectDto).collect(Collectors.toList());
		}

		else if (!description.isEmpty()) {
			logger.info("description value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByDescriptionContainsIgnoreCase(description, pageable).stream()
					.map(this::convertProjectDto).collect(Collectors.toList());
		} else if (!contactPersonName.isEmpty()) {
			logger.info("contact Person Name value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByContactPersonNameStartsWithIgnoreCase(contactPersonName, pageable).stream()
					.map(this::convertProjectDto).collect(Collectors.toList());
		} else if (!contactPersonMobileNumber.isEmpty()) {
			logger.info("contact Person Mobile Number value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByContactPersonMobileNumberStartsWith(contactPersonMobileNumber, pageable)
					.stream().map(this::convertProjectDto).collect(Collectors.toList());
		} else if (!contactPersonEmailId.isEmpty()) {
			logger.info("contact Person EmailId value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByContactPersonEmailIdStartsWithIgnoreCase(contactPersonEmailId, pageable)
					.stream().map(this::convertProjectDto).collect(Collectors.toList());
		} else if (!(endDate.isEmpty())) {
			logger.info("end date value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByEndDate(endDate, pageable).stream().map(this::convertProjectDto)
					.collect(Collectors.toList());
		} else if (!(startDate.isEmpty())) {
			logger.info("start date value passed");
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return projectRepository.findByStartDate(startDate, pageable).stream().map(this::convertProjectDto)
					.collect(Collectors.toList());

		}
		logger.info("all project values passed");
		Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
		return projectRepository.findAll(pageable).stream().map(this::convertProjectDto).collect(Collectors.toList());
	}

}
