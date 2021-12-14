package com.sgic.defect.server.controller;

import java.util.List;

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

import com.sgic.defect.server.dto.DefectDto;
import com.sgic.defect.server.dto.DefectFindAllDto;
import com.sgic.defect.server.dto.DefectUpdateDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.DefectService;

@RestController
public class DefectController {

	@Autowired
	DefectService defectService;
	private final Logger logger = LoggerFactory.getLogger(DefectController.class);

	@PostMapping("/defect")
	public ResponseEntity<Object> saveDefect(@RequestBody DefectDto defectDto) {

		if (defectService.validateField(defectDto)) {
			defectService.saveDefect(defectDto);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			logger.info("save defect success");
			return ResponseEntity.ok(responseDto);
		}
		logger.info("save defect Failed");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/defect/{id}")
	public ResponseEntity<Object> updateDefect(@PathVariable Long id, @RequestBody DefectUpdateDto defectUpdateDto) {
		if (defectService.existsByDefectId(id)) {
			defectService.updateDefect(id, defectUpdateDto);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			logger.info("Update defect success");
			return ResponseEntity.ok(responseDto);
		}
		logger.info("Update defect Failed");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@GetMapping("/defects/{projectId}")
	public List<DefectFindAllDto> findAllDefect(@PathVariable Long projectId,
			@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "asc") String direction,
			@RequestParam(required = false, defaultValue = "defectId") String property,
			@RequestParam(name = "severityName") String severityName,
			@RequestParam(required = false, defaultValue = "") String priorityName,
			@RequestParam(required = false, defaultValue = "") String defectStatusName,
			@RequestParam(required = false, defaultValue = "") String defectTypeName,
			@RequestParam(required = false, defaultValue = "") String subModuleName,
			@RequestParam(required = false, defaultValue = "") String releaseName,
			@RequestParam(required = false, defaultValue = "") Long assignedUserId,
			@RequestParam(required = false, defaultValue = "") Long creativeUserId) {
		logger.info("get all defect started");
		return defectService.findAlldefect(projectId, pageNumber, direction, property, severityName, priorityName,
				defectStatusName, defectTypeName, subModuleName, releaseName, assignedUserId, creativeUserId);
	}

	@GetMapping("/defect/{defectId}")
	public ResponseEntity<Object> findByIdDefect(@PathVariable("defectId") Long defectId) {
		if (defectService.existsByDefectId(defectId)) {
			logger.info("get project started");
			return ResponseEntity.ok(defectService.findByIdDefect(defectId));
		}
		logger.info("get project Failed");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/defect/{defectId}")
	public ResponseEntity<Object> deletedefect(@PathVariable Long defectId) {
		ResponseDto responseDto = new ResponseDto();
		defectService.deleteDefect(defectId);

		responseDto.setCode(RestApiResponseStatus.OK.getCode());
		responseDto.setStatus(RestApiResponseStatus.OK.getStatus());
		logger.info(" defect is sucessfully deleted!");
		return ResponseEntity.ok(responseDto);

	}
}
