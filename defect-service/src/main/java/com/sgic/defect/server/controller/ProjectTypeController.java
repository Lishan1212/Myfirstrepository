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

import com.sgic.defect.server.dto.ProjectTypeDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.ProjectTypeService;

@RestController

public class ProjectTypeController {
	@Autowired
	ProjectTypeService projectTypeService;

	private final Logger logger = LoggerFactory.getLogger(PriorityController.class);

	@PostMapping("/projecttype")
	public ResponseEntity<Object> saveProjectType(@Valid @RequestBody ProjectTypeDto projectTypeDto) {
		logger.info("Project type Save Started !");

		ResponseDto responseDto = new ResponseDto();
		if (projectTypeService.validateDuplicate(projectTypeDto)) {

			projectTypeService.saveProjectType(projectTypeDto);
			logger.info("Save priority sucessfully !", projectTypeDto);
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}

		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/projecttypes/{pageNumber}/{pageSize}")
	public List<ProjectTypeDto> allProjectType(@PathVariable @RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		logger.info("Project type Save Started !");
		return projectTypeService.allProjectType(pageNumber, pageSize);
	}

	@PutMapping("/projecttype/{projectTypeId}")
	public ResponseEntity<Object> updateprojectType(@PathVariable Long projectTypeId,
			@Valid @RequestBody ProjectTypeDto projectTypeDto) {

		logger.info("Project type update Started !");

		ResponseDto responseDto = new ResponseDto();
		if (projectTypeService.validateDuplicate(projectTypeDto)) {
			projectTypeService.updateprojectType(projectTypeId, projectTypeDto);
			logger.info("Update sucessfully !", projectTypeDto);

			responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}
		responseDto.setCode(RestApiResponseStatus.ERROR.getCode());
		responseDto.setStatus(RestApiResponseStatus.ERROR.getStatus());
		return ResponseEntity.ok(responseDto);

	}
	@DeleteMapping("deleteprojecttype/{id}")
	public ResponseEntity<Object>deletePriority(@PathVariable Long id)
	{
		if(projectTypeService.isExistById(id))
		{
			if(!(projectTypeService.extistdefectTypeIndefecttype(id)))
			{
				logger.info("Defecttype is Deleted");
				projectTypeService.deleteProjecttype(id);
				return ResponseEntity.ok("Defectype Deleted");
			}
			logger.info("Defecttype is not Deleted");
			return ResponseEntity.ok("Defectype not Deleted");
		}
		logger.info("Defect type are not deleted");
		return ResponseEntity.ok("Defectype Deleted");
	}

}
