package com.sgic.defect.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.defect.server.dto.ProjectDto;
import com.sgic.defect.server.dto.ProjectNameDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.ProjectService;
import com.sgic.defect.server.util.ErrorCodes;

@RestController
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	ErrorCodes errorCode;

	private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@PostMapping("/project")
	public ResponseEntity<Object> saveProject(@Valid @RequestBody ProjectDto projectDto) {

		if (projectService.validateProjectName(projectDto.getProjectName())) {
			if (projectService.validateProjectTypeID(projectDto.getProjectTypeId())) {
				logger.info("saving project started");
				projectService.saveProject(projectDto);
				ResponseDto responseDto = new ResponseDto();
				responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
				responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
				logger.info("save project Succeed");
				return ResponseEntity.ok(responseDto);
			}
			logger.info("save project failed by Project Type ID");
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
			responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
			return ResponseEntity.ok(responseDto);

		}
		logger.info("save project failed Project Name ");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@PutMapping("/project/{projectId}")
	public ResponseEntity<Object> updateProject(@PathVariable Long projectId,
			@Valid @RequestBody ProjectDto projectDto) {

		if (projectService.validateProjectId(projectId)) {
			if (projectService.validateProjectName(projectDto.getProjectName())) {
				logger.info("Update project started");
				projectService.updateProject(projectId, projectDto);
				ResponseDto responseDto = new ResponseDto();
				responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
				responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
				logger.info("update project Succeed");
				return ResponseEntity.ok(responseDto);
			}
			logger.info("Update project failed by Project Name");
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
			responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
			return ResponseEntity.ok(responseDto);

		}
		logger.info("Update project failed by Project Id");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@GetMapping("/project/{projectId}")
	public ResponseEntity<Object> findProjectById(@PathVariable Long projectId) {

		if (projectService.findProjectById(projectId) != null) {
			logger.info("get project started");
			return ResponseEntity.ok(projectService.findProjectById(projectId));
		}
		logger.info("get project failed by Project ID");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/project/names")
	public List<ProjectNameDto> getProjectName() {
		logger.info("get project name started");
		return projectService.getProjectName();
	}

	@GetMapping("/projects")
	public ResponseEntity<Object> findAllProject(@RequestParam("projectName") String projectName,
			@RequestParam("statusName") String statusName, @RequestParam("projectType") String projectType,
			@RequestParam("prefix") String prefix, @RequestParam("description") String description,
			@RequestParam("contactPersonName") String contactPersonName,
			@RequestParam("contactPersonMobileNumber") String contactPersonMobileNumber,
			@RequestParam("contactPersonEmailId") String contactPersonEmailId,
			@RequestParam("clientAddress") String clientAddress, @RequestParam("clientName") String clientName,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "asc") String direction,
			@RequestParam(required = false, defaultValue = "projectId") String property) {

		List<ProjectDto> projectList = projectService.findAllProject(projectName, clientAddress, clientName, statusName,
				projectType, prefix, description, contactPersonName, contactPersonMobileNumber, contactPersonEmailId,
				startDate, endDate, pageNumber, property, direction);

		return ResponseEntity.ok(projectList);

	}

}
