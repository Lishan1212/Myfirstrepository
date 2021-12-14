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

import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.dto.SeverityDto;
import com.sgic.defect.server.dto.SeverityIdDto;
import com.sgic.defect.server.dto.SeverityNameDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.SeverityService;

@RestController
public class SeverityController {
	@Autowired
	public SeverityService severityService;
	Logger logger = LoggerFactory.getLogger(SeverityController.class);

	@PostMapping("/severity")
	public ResponseEntity<Object> severitys(@Valid @RequestBody SeverityDto severityDto) {
		if (severityService.isSeverityNameValidate(severityDto.getName())) {
			if (severityService.isSeverityColorValidate(severityDto.getColor())) {
				logger.info("severity save started");
				severityService.saveSeverity(severityDto);
				ResponseDto response = new ResponseDto();
				response.setCode(RestApiResponseStatus.CREATED.getCode());
				response.setStatus(RestApiResponseStatus.CREATED.getStatus());
				logger.info("severity save succeed");
				return ResponseEntity.ok(response);
			}
			logger.info("severity save failed by projectName");
			ResponseDto response = new ResponseDto();
			response.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
			response.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
			return ResponseEntity.ok(response);
		}
		logger.info("severity save failed by projectColor");
		ResponseDto response = new ResponseDto();
		response.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/severity/{pageNo}/{pageSize}")

	public List<SeverityIdDto> getallSeverity(@PathVariable @RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "name") String name) {
		logger.info("severity details getall start");
		return severityService.findAllSeverity(pageNumber, pageSize, name);

	}

	@PutMapping("/severity/{severityId}")

	public ResponseEntity<Object> updateById(@PathVariable Long severityId,
			@Valid @RequestBody SeverityDto severityDto) {
		if (severityService.isSeverityNameValidate(severityDto.getName())) {
			if (severityService.isSeverityColorValidate(severityDto.getColor())) {
				logger.info("severity update start");
				severityService.updateSeverity(severityId, severityDto);
				ResponseDto response = new ResponseDto();
				response.setCode(RestApiResponseStatus.UPDATED.getCode());
				response.setStatus(RestApiResponseStatus.UPDATED.getStatus());
				logger.info("update severity succeed");
				return ResponseEntity.ok(response);
			}
			logger.info("severity update failed by projectColor");
			ResponseDto response = new ResponseDto();
			response.setCode(RestApiResponseStatus.ERROR.getCode());
			response.setStatus(RestApiResponseStatus.ERROR.getStatus());
			return ResponseEntity.ok(response);
		}
		logger.info("severity update failed by projectColor");
		ResponseDto response = new ResponseDto();
		response.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/severitynames")
	public List<SeverityNameDto> getallSeverityNames() {
		logger.info("severity   getallSeverityname start ");
		return severityService.findSeverityByName(null);

	}

	@GetMapping("/severityproject/{severityId}")
	public ResponseEntity<Object> getAllSeveritiesByProjectId(@PathVariable Long severityId) {
		if (severityService.validateProjectId(severityId)) {
			logger.info("get all severity by project ID started ");
			List severList = severityService.getAllSeveritiesByProjectId(severityId);
			logger.info("get all severity by project ID Succeed ");
			return ResponseEntity.ok(severList);
		}
		logger.info("getallseverityby projectID Failed by ProjectID  ");
		ResponseDto response = new ResponseDto();
		response.setCode(RestApiResponseStatus.FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/severity/{severityId}")

	public Object findById(@PathVariable Long severityId, SeverityDto severityDto) {
		ResponseDto response = new ResponseDto();
		if (severityService.findSeverityById(severityId) != null) {
			logger.info(" successfully find");
			return severityService.findSeverityById(severityId);
		}
		logger.info("findBySeverityId failedby severityId exit");
		response.setCode(RestApiResponseStatus.FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return (response);
	}

	@GetMapping("/searchseverity")
	public ResponseEntity<Object> searchSeverity(@RequestParam("name") String name, @RequestParam("color") String color,
			@RequestParam("severityLevel") String severityLevel) {
		logger.info(" successfully search");
		List<SeverityDto> severityList = severityService.searchSeverity(severityLevel, name, color);

		return ResponseEntity.ok(severityList);

	}

	@DeleteMapping("deleteseverity/{severityId}")
	public ResponseEntity<Object> deleteSeverities(@PathVariable Long severityId) {
		logger.info("severity findById start");
		ResponseDto response = new ResponseDto();
		if (severityService.isFindSeverityByIdValidate(severityId)) {
			if ((!severityService.validateSeverityIdinDefect(severityId))) {
				severityService.deleteSeverity(severityId);
				logger.info("severity delete success");
				response.setCode(RestApiResponseStatus.DELETED.getCode());
				response.setStatus(RestApiResponseStatus.DELETED.getStatus());
				return ResponseEntity.ok(response);
			}
			logger.info("severity delete fail by defectId");
			response.setCode(RestApiResponseStatus.EXISTS.getCode());
			response.setStatus(RestApiResponseStatus.EXISTS.getStatus());
			return ResponseEntity.ok(response);

		}
		logger.info("severity delete fail by severityId exit");
		response.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(response);
	}
}
