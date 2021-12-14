package com.sgic.employee.server.controller;

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

import com.sgic.employee.server.dto.DesignationDto;
import com.sgic.employee.server.dto.DesignationErrorDto;
import com.sgic.employee.server.enums.RestApiResponseStatus;
import com.sgic.employee.server.services.DesignationService;

@RestController
public class DesignationController {
	@Autowired
	private DesignationService designationService;

	private final Logger logger = LoggerFactory.getLogger(DesignationController.class);

	@PostMapping("/designation")
	public ResponseEntity<Object> saveDesignation(@Valid @RequestBody DesignationDto designationDto) {
		logger.info("Save designation started");
		if (designationService.validateError(designationDto)) {
			designationService.saveDesignation(designationDto);
			DesignationErrorDto designationErrorDto = new DesignationErrorDto();
			designationErrorDto.setCode(RestApiResponseStatus.CREATED.getCode());
			designationErrorDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			return ResponseEntity.ok(designationErrorDto);
		}

		DesignationErrorDto designationErrorDto = new DesignationErrorDto();
		designationErrorDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		designationErrorDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(designationErrorDto);
	}

	@DeleteMapping("/designation/{id}")
	public ResponseEntity<Object> deleteDesignation(@PathVariable Long id) {
		logger.info("delete designation started");

		if (designationService.existsDesignationId(id)) {
			logger.info("delete designation existsDesignationId passed");
			if (designationService.validateDesignationInEmployee(id)) {
				logger.info("delete designation validateDesignationInEmployee passed");

				designationService.deleteDesignation(id);
				DesignationErrorDto designationErrorDto = new DesignationErrorDto();
				designationErrorDto.setCode(RestApiResponseStatus.DELETED.getCode());
				designationErrorDto.setStatus(RestApiResponseStatus.DELETED.getStatus());
				return ResponseEntity.ok(designationErrorDto);
			}
			logger.info("delete designation validateDesignationInEmployee failed");
			DesignationErrorDto designationErrorDto = new DesignationErrorDto();
			designationErrorDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
			designationErrorDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
			return ResponseEntity.ok(designationErrorDto);
		}

		logger.info("delete designation existsDesignationId failed");
		DesignationErrorDto designationErrorDto = new DesignationErrorDto();
		designationErrorDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		designationErrorDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(designationErrorDto);
	}

	@PutMapping("/designation/{id}")
	public ResponseEntity<Object> updateDesignation(@PathVariable Long id,
			@Valid @RequestBody DesignationDto designationDto) {
		logger.info("Update designation started");
		if (designationService.validateErrorUpdate(designationDto)) {
			designationService.updateDesignation(id, designationDto);
			DesignationErrorDto designationErrorDto = new DesignationErrorDto();
			designationErrorDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			designationErrorDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			return ResponseEntity.ok(designationErrorDto);
		}
		DesignationErrorDto designationErrorDto = new DesignationErrorDto();
		designationErrorDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		designationErrorDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(designationErrorDto);
	}

	@GetMapping("/designations")
	public List<DesignationDto> getAllDesignation(
			@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = false, defaultValue = "asc") String direction,
			@RequestParam(required = false, defaultValue = "designationId") String property,
			@RequestParam(required = false, defaultValue = "") String designationName) {

		logger.info("Get all designations started");
		return designationService.getAllDesignation(pageNumber, pageSize, direction, property, designationName);
	}

}
