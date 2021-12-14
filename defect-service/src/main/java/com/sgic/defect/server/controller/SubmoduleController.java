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
import org.springframework.web.bind.annotation.RestController;

import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.dto.SubmoduleDto;
import com.sgic.defect.server.dto.SubmoduleNameDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.ModuleServiceImpl;
import com.sgic.defect.server.services.SubmoduleService;

@RestController
public class SubmoduleController {
	@Autowired
	SubmoduleService submoduleService;
	@Autowired
	ModuleServiceImpl moduleServiceImpl;
	private final Logger logger = LoggerFactory.getLogger(SubmoduleController.class);

	@PostMapping("/submodule")
	public ResponseEntity<Object> saveModule(@RequestBody SubmoduleDto submoduleDto) {

		if (moduleServiceImpl.validateModuleId(submoduleDto.getModuleId())) {
			logger.info("submodule save started in controller");

			if (!submoduleService.validateeModuleDuplicateValue(submoduleDto)) {
				logger.info("submodule validation check started");
				ResponseDto responseDto = new ResponseDto();
				responseDto.setCode(RestApiResponseStatus.EXISTS.getCode());
				responseDto.setStatus(RestApiResponseStatus.EXISTS.getStatus());
				logger.info("save Submodule failed - duplicate value");
				return ResponseEntity.ok(responseDto);
			}

			submoduleService.saveSubmodule(submoduleDto);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			logger.info("save Submodule success");
			return ResponseEntity.ok(responseDto);
		}

		logger.info("Save submodule Failed");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/submodule/{id}")
	public ResponseEntity<Object> updateModule(@PathVariable Long id, @RequestBody SubmoduleNameDto submoduleNameDto) {

		if (moduleServiceImpl.validateModuleId(id)) {
			logger.info("Update submodule started");
			logger.info("submodule update started in controller");
			submoduleService.updateSubmdule(id, submoduleNameDto);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			logger.info("submodule module success");
			return ResponseEntity.ok(responseDto);
		}
		logger.info("Update module Failed");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@GetMapping("/submodules/{moduleId}")
	public List<SubmoduleNameDto> findAllModuleByModuleId(@PathVariable Long moduleId) {
		logger.info("FindAll submodule started");
		return submoduleService.findAllSubmoduleByModuleId(moduleId);
	}

	@GetMapping("/submodule/{subModuleId}")
	public ResponseEntity<Object> findSubmoduleBySubmoduleId(@Valid @PathVariable Long subModuleId) {
		logger.info("Submodule find by id started");
		if (submoduleService.validateSubmoduleId(subModuleId)) {
			logger.info("Submodule ID is valid");
			return ResponseEntity.ok(submoduleService.findSubmoduleById(subModuleId));
		}
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}
	
	@DeleteMapping("/submodule/{subModuleId}")
	public ResponseEntity<Object> deleteBySubmoduleId(@PathVariable Long subModuleId){
		logger.info("Submodule deletion completed");
		ResponseDto responseDto = new ResponseDto();
		
		if(submoduleService.existSubmoduleInModuleAllocation(subModuleId)) {
			submoduleService.deleteBySubmoduleId(subModuleId);
			responseDto.setCode(RestApiResponseStatus.OK.getCode());
			responseDto.setStatus(RestApiResponseStatus.OK.getStatus());
			logger.info("The submodule used in module allocation");
			return ResponseEntity.ok(responseDto);
		}
				
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		logger.info("Submodule deletion failed");
		return ResponseEntity.ok(responseDto);
		
	}
}
