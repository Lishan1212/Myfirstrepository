package com.sgic.defect.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.defect.server.dto.DefectStatusDto;
import com.sgic.defect.server.dto.DefectStatusErrorDto;
import com.sgic.defect.server.dto.DefectStatusFindAllDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.DefectStatusService;

@RestController
public class DefectStatusController {

	@Autowired
	DefectStatusService defectStatusService;
	private final Logger logger = LoggerFactory.getLogger(DefectStatusController.class);
	DefectStatusErrorDto defectStatusErrorDto = new DefectStatusErrorDto();

	@PostMapping("/defectstatus")
	public ResponseEntity<Object> saveDefectStatus(@Valid @RequestBody DefectStatusDto defectStatusDto) {
		logger.info("save DefectStatus Started");
		if (defectStatusService.validateField(defectStatusDto)) {
			if (defectStatusService.isDefectStatusNameValidate(defectStatusDto.getDefectStatusName())) {
				if (defectStatusService.isDefectStatusColorValidate(defectStatusDto.getDefectStatusColor())) {
					defectStatusService.saveDefectStatus(defectStatusDto);
					defectStatusErrorDto.setCode(RestApiResponseStatus.CREATED.getCode());
					defectStatusErrorDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
					logger.info("Created defectStatus successfully");
					return ResponseEntity.ok(defectStatusErrorDto);
				}
			}
		}

		defectStatusErrorDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		defectStatusErrorDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		logger.info("save DefectStatus failure");
		return ResponseEntity.ok(defectStatusErrorDto);

	}

	@PutMapping("/defectstatus/{id}")
	public ResponseEntity<Object> updateDefectStatus(@PathVariable Long id,
			@RequestBody DefectStatusDto defectStatusDto) {
		logger.info("update DefectStatus Started");
		if (defectStatusService.validateField(defectStatusDto)) {
			if (defectStatusService.isDefectStatusNameValidate(defectStatusDto.getDefectStatusName())) {
				if (defectStatusService.isDefectStatusColorValidate(defectStatusDto.getDefectStatusColor())) {
		defectStatusService.updateDefectStatus(id, defectStatusDto);
		DefectStatusErrorDto defectStatusErrorDto = new DefectStatusErrorDto();
		defectStatusErrorDto.setCode(RestApiResponseStatus.UPDATED.getCode());
		defectStatusErrorDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
		logger.info("DefectStatus Updated");
		return ResponseEntity.ok(defectStatusErrorDto);
		
				}
			}
		}

		defectStatusErrorDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		defectStatusErrorDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		logger.info("save DefectStatus failure");
		return ResponseEntity.ok(defectStatusErrorDto);
	}

	@GetMapping("/defectstatuses")
	public List<DefectStatusFindAllDto> findAllDefectStatus(@RequestParam int pageNumber, @RequestParam int pageSize,
			Pageable pageable) {
		logger.info(" Found allDefectStatus ");
		return defectStatusService.defectStatusFindAllDto(pageNumber, pageSize);

	}

	@GetMapping("/defectstatus/{projectid}")
	public List<DefectStatusDto> findDefectStatusByProjectId(@PathVariable Long id) {
		logger.info("Listed defect statuses  Details");
		DefectStatusDto defectStatusDto = new DefectStatusDto();
		if (!(defectStatusService.validateField(defectStatusDto))) {
			return defectStatusService.findDefectStatusByProjectId(id);
		}
		logger.info("Error");
		return null;
	}

	@GetMapping("/searchdefectstatus")
	public ResponseEntity<Object> Searchdefectstatus(
			@RequestParam(required = false, defaultValue = "") String defectStatusName,
			@RequestParam(required = false, defaultValue = "") String defectStatusColor) {
		logger.info("Search started");
		List<DefectStatusFindAllDto> defectstatusnames = defectStatusService.findDefectStatusByName(defectStatusName,
				defectStatusColor);
		return ResponseEntity.ok(defectstatusnames);
	}

	@DeleteMapping("/deletedefectstatus/{defectStatusId}")
	public ResponseEntity<Object> deletedefectstatus(@PathVariable Long defectStatusId) {
		logger.info("delete method is started");
		if (defectStatusService.isExistDefectStatusIdInDefect(defectStatusId)) {
			defectStatusService.deleteDefectStatus(defectStatusId);
			defectStatusErrorDto.setStatus(RestApiResponseStatus.OK.getStatus());
			defectStatusErrorDto.setCode(RestApiResponseStatus.OK.getCode());
			logger.info("defect status deleted successfully");
			return ResponseEntity.ok(defectStatusErrorDto);
		}
		defectStatusErrorDto.setStatus(RestApiResponseStatus.ERROR.getStatus());
		defectStatusErrorDto.setCode(RestApiResponseStatus.ERROR.getCode());
		logger.info("defect status not deleted ");
		return ResponseEntity.ok(defectStatusErrorDto);

	}

}
