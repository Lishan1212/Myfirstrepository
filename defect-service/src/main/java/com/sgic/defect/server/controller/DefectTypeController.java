package com.sgic.defect.server.controller;

import java.util.List;

import javax.validation.Valid;

//import org.hibernate.annotations.common.util.impl.Log_.logger;
//import org.hibernate.annotations.common.util.impl.Log_.logger;
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

import com.sgic.defect.server.dto.DefectTypeDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
//import com.sgic.defect.server.repositories.DefectTypeRepository;
import com.sgic.defect.server.services.DefectTypeServices;

@RestController
public class DefectTypeController {

	@Autowired
	DefectTypeServices defectTypeServices;

	private final Logger Logger = LoggerFactory.getLogger(DefectTypeController.class);

	@PostMapping("/defecttype")
	public ResponseEntity<Object> saveDefectType(@Valid @RequestBody DefectTypeDto defectTypeDto) {

		Logger.info(" defectType save Started");
		ResponseDto responseDto = new ResponseDto();
		if (defectTypeServices.validateduplicate(defectTypeDto)) {
			defectTypeServices.saveDefectType(defectTypeDto);
			Logger.info("Save priority sucessfully !", defectTypeDto);
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}
		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		Logger.info("Save priority error !");
		return ResponseEntity.ok(responseDto);

	}

	@GetMapping("/defecttypes/{pageNumber}/{pageSize}")
	public List<DefectTypeDto> alldefect(@PathVariable @RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		Logger.info(" viewDefect type Started");
		return defectTypeServices.findAllDefect(pageNumber, pageSize);
	}

	@PutMapping("/defecttype/{id}")
	public ResponseEntity<Object> updateDefecttype(@PathVariable Long defectTypeId,
			@Valid @RequestBody DefectTypeDto defectTypeDto) {
		Logger.info(" update type Started");
		ResponseDto responseDto = new ResponseDto();

		if (defectTypeServices.validateduplicate(defectTypeDto)) {

			defectTypeServices.updateDefecttype(defectTypeId, defectTypeDto);
			responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}

		responseDto.setCode(RestApiResponseStatus.ERROR.getCode());
		responseDto.setStatus(RestApiResponseStatus.ERROR.getStatus());
		Logger.info("update priority error !");
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/defecttypeProject/{projectId}")
	public ResponseEntity<Object> getAllDefectTypeByProject(@PathVariable Long projectId) {
		Logger.info("start to get all defect type by project id");
		ResponseDto responseDto = new ResponseDto();
		if (defectTypeServices.validateProjectId(projectId)) {
			defectTypeServices.getAllDefectTypeByProjectId(projectId);
			return ResponseEntity.ok(defectTypeServices.getAllDefectTypeByProjectId(projectId));

		}

		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		Logger.info("get priority by project id error !");
		return ResponseEntity.ok(responseDto);

	}

	@GetMapping("/defecttypesearch")
	public ResponseEntity<Object> Searchdefecttype(
			@RequestParam(required = false, defaultValue = "") String defectTypeName) {
		Logger.info("Search started");
		List<DefectTypeDto> defecttypenamelist = defectTypeServices.defectTypeSerach(defectTypeName);
		return ResponseEntity.ok(defecttypenamelist);
	}

	@DeleteMapping("/deletedefecttype/{id}")
	public ResponseEntity<Object> deleteDefectType(@PathVariable Long id) {
		if (defectTypeServices.isexistById(id)) {
			if (!defectTypeServices.existsByDefectTypeInDefectType(id)) {
				defectTypeServices.deleteDefectType(id);
				return ResponseEntity.ok("defect type deleted");
			}
			return ResponseEntity.ok("defect type not deleted");
		}
		return ResponseEntity.ok("sucessfully deleted  " + id);

	}
}
