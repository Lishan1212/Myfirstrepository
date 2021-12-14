package com.sgic.defect.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.defect.server.dto.ProjectAllocationDto;
import com.sgic.defect.server.dto.ProjectAllocationIdDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.ProjectAllocationService;

@RestController
public class ProjectAllocationController {
	@Autowired
	ProjectAllocationService projectAllocationService;
	final Logger logger = LoggerFactory.getLogger(ProjectAllocationController.class);

	@PostMapping("/projectallocation")
	public ResponseEntity<Object> projectAllocations(@Valid @RequestBody ProjectAllocationDto projectAllocationDto) {
		logger.info("project name validated");
		if (projectAllocationService.isProjectAllocationValidbyEmployeeId(projectAllocationDto.getEmployeeId())) {
			if (projectAllocationService.isProjectAllocationfullNameAndProjectIdValidate(
					projectAllocationDto.getFullName(), projectAllocationDto.getProjectId())) {
				logger.info("percentage validation passed");
				if (projectAllocationService.isProjectAllocationPercentageValidation(
						projectAllocationDto.getEmployeeId(), projectAllocationDto.getPercentage())) {
					logger.info("ProjectAllocation save started");
					projectAllocationService.saveProjectAllocation(projectAllocationDto);
					ResponseDto response = new ResponseDto();
					response.setCode(RestApiResponseStatus.CREATED.getCode());
					response.setStatus(RestApiResponseStatus.CREATED.getStatus());
					logger.info("projectAllocation save succeed");
					return ResponseEntity.ok(response);
				}

				logger.info("allocation percentage out of availability");

				ResponseDto response = new ResponseDto();
				response.setCode(RestApiResponseStatus.FAILURE.getCode());
				response.setStatus(RestApiResponseStatus.FAILURE.getStatus());
				return ResponseEntity.ok(response);
			}
			logger.info("allocation out by fullName");
			ResponseDto response = new ResponseDto();
			response.setCode(RestApiResponseStatus.EXISTS.getCode());
			response.setStatus(RestApiResponseStatus.EXISTS.getStatus());
			return ResponseEntity.ok(response);
		}
		logger.info("allocation out by employeeId");
		ResponseDto response = new ResponseDto();
		response.setCode(RestApiResponseStatus.ERROR.getCode());
		response.setStatus(RestApiResponseStatus.ERROR.getStatus());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/projectallocation/{projectAllocationId}")
	public ResponseEntity<Object> updateById(@Valid @PathVariable("projectAllocationId") Long projectAllocationId,
			@RequestBody ProjectAllocationDto projectAllocationDto) {
		if (projectAllocationService.isProjectAllocationfullNameAndProjectIdValidate(
				(projectAllocationDto.getFullName()), projectAllocationDto.getProjectId())) {
			logger.info("allocation update started");
			projectAllocationService.updateProjectAllocation(projectAllocationId, projectAllocationDto);
			ResponseDto response = new ResponseDto();
			response.setCode(RestApiResponseStatus.UPDATED.getCode());
			response.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			logger.info("allocation update succeed");
			return ResponseEntity.ok(response);
		}

		logger.info("allocation update  out by fullName");
		ResponseDto response = new ResponseDto();
		response.setCode(RestApiResponseStatus.ERROR.getCode());
		response.setStatus(RestApiResponseStatus.ERROR.getStatus());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/projectallocations/{pageNunber}/{pageSize}")
	public List<ProjectAllocationIdDto> getallproAllocation(
			@PathVariable @RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "5") Integer pageSize,
			@RequestParam(defaultValue = "fullName") String sortBy) {
		logger.info("allocation getall Started");
		return projectAllocationService.findAllProjectAllocation(pageNumber, pageSize, sortBy);
	}

	@GetMapping("/projectallocation/{projectAllocationId}")
	public Object findById(@PathVariable("projectAllocationId") Long projectAllocationId,
			ProjectAllocationDto projectAllocationDto) {
		logger.info("successfyully find");
		return projectAllocationService.findProjectAllocationById(projectAllocationId);
	}


	@GetMapping("/ProjectAllocationId/{projectId}")
	public ResponseEntity<Object> getAllByProjectId(@PathVariable Long projectId) {
		if (projectAllocationService.isProjectAllocationValidbyProjectId(projectId)) {
			return ResponseEntity.ok(projectAllocationService.getAllProjectAllocationByProjectId(projectId));
		}
		ResponseDto response = new ResponseDto();
		logger.info("ProjectAllocation details getall Started");
		response.setCode(RestApiResponseStatus.FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(response);

	}


	@DeleteMapping("/deleteprojectallocation/{projectAllocationId}")
	public ResponseEntity<Object> deleteProjectAllocation(@PathVariable Long projectAllocationId) {
		logger.info("projectAllocation delete start");
		ResponseDto response = new ResponseDto();
		if (projectAllocationService.isFindProjectAllocationByIdValidate(projectAllocationId)) {
			projectAllocationService.deleteProjectAllocation(projectAllocationId);
			logger.info("projectAllocation delete success");
			response.setCode(RestApiResponseStatus.DELETED.getCode());
			response.setStatus(RestApiResponseStatus.DELETED.getStatus());
			return ResponseEntity.ok(response);

		}
		logger.info("projectAllocation delete fail by projectAllocationId exit");
		response.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/projectallocationsearch")
	public List<ProjectAllocationDto> projectAllocationSearch(
			@RequestParam(required = false, defaultValue = "") Long employeeId,
			@RequestParam(required = false, defaultValue = "") String fullName,
			@RequestParam(required = false, defaultValue = "") String roleName,
			@RequestParam(required = false, defaultValue = "") Long percentage,
			@RequestParam(required = false, defaultValue = "") String projectName) {
		logger.info("ProjectAllocation search Started");
		return projectAllocationService.projectAllocationSearch(employeeId, fullName, roleName, percentage,
				projectName);
	}
}
