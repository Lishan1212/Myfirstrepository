package com.sgic.defect.server.controller;

import java.util.List;

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

import com.sgic.defect.server.dto.ModuleAllocationGetDto;
import com.sgic.defect.server.dto.ModuleAllocationSaveDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.ModuleAllocationServices;

import ch.qos.logback.classic.Logger;

@RestController
public class ModuleAllocationController {
	Logger logger = (Logger) LoggerFactory.getLogger(ModuleAllocationController.class);
	@Autowired
	ModuleAllocationServices moduleAllocationServices;
	
	ResponseDto response = new ResponseDto();

	@PostMapping("/moduleallocation")
	public ResponseEntity<Object> saveModuleAllocation(@RequestBody ModuleAllocationSaveDto moduleAllocationDto) {
		logger.info("Module Allocation save start!");
		
		if (moduleAllocationServices.isExistSubmodule(moduleAllocationDto.getSubModuleId())) {

			if (moduleAllocationServices.moduleAllocationValidation(moduleAllocationDto.getSubModuleId())) {
				moduleAllocationServices.savemoduleAllocation(moduleAllocationDto);
				response.setCode(RestApiResponseStatus.CREATED.getCode());
				response.setStatus(RestApiResponseStatus.CREATED.getStatus());
				logger.info("module allocation is succeed");
				return ResponseEntity.ok(response);
			}

			response.setCode(RestApiResponseStatus.EXISTS.getCode());
			response.setStatus(RestApiResponseStatus.EXISTS.getStatus());
			logger.info("Module Allocation failed - already one person allowed");
			return ResponseEntity.ok(response);
		}

		response.setCode(RestApiResponseStatus.FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		logger.info("Module Allocation failed - sub module not exist");
		return ResponseEntity.ok(response);

//		response.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
//		response.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
//		logger.info("Module Allocation create failed");
//		return ResponseEntity.ok(response);
	}

	@PutMapping("/moduleallocation/{id}")
	public ResponseEntity<Object> updateModuleAllocationById(@PathVariable Long moduleAllocationId,
			@RequestBody ModuleAllocationGetDto moduleAllocationDto2) {
		logger.info("Module allocation entity update start");
		ResponseDto response = new ResponseDto();
		if (moduleAllocationServices.fullNameValidation(moduleAllocationDto2.getFullName())) {
			moduleAllocationServices.updateModuleAllocation(moduleAllocationId, moduleAllocationDto2);

			response.setCode(RestApiResponseStatus.OK.getCode());
			response.setStatus(RestApiResponseStatus.OK.getStatus());
			logger.info("Module Allocation updated sucessfully");
			return ResponseEntity.ok(response);
		}
		// error code
		response.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		logger.info("Module Allocation update failed");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/moduleallocations/{pageNmber}/{pageSize}")
	public List<ModuleAllocationGetDto> getAllModuleAllocation(@PathVariable @RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		logger.info("Module allocation entity view all start");
		return moduleAllocationServices.getAllModuleAllocation(pageNumber, pageSize);
	}

	@GetMapping("/moduleallocation/{id}")

	public ResponseEntity<Object> findById(@PathVariable Long moduleAllocationId) {

		logger.info("get module allocation id start");
		ResponseDto response = new ResponseDto();
		if (moduleAllocationServices.moduleAllocationIdValidation(moduleAllocationId)) {
			logger.info("get module allocation id success!");
			return ResponseEntity.ok(moduleAllocationServices.findById(moduleAllocationId));
		}
		response.setCode(RestApiResponseStatus.FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		logger.info("Id is not exist");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/moduleallocation/deallocation/{moduleAllocationId}")
	public ResponseEntity<Object> moduleDeAllocation(@PathVariable Long moduleAllocationId) {
		if (moduleAllocationServices.moduleAllocationIdValidation(moduleAllocationId)) {
			moduleAllocationServices.moduleDeAllocation(moduleAllocationId);
			response.setCode(RestApiResponseStatus.OK.getCode());
			response.setStatus(RestApiResponseStatus.OK.getStatus());
			logger.info("module deallocation is success");
			return ResponseEntity.ok(response);
		}
		
		response.setCode(RestApiResponseStatus.FAILURE.getCode());
		response.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		logger.info("module allocation id not found");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/moduleallocationsearch")
	public ResponseEntity<Object> searchModuleAllocation(@RequestParam(required = false, defaultValue ="" ) String fullName)
	{
		logger.info("Search started");
		List<ModuleAllocationGetDto> moduleAllocationlist=moduleAllocationServices.moduleAllocationsearch(fullName);
		return ResponseEntity.ok(moduleAllocationlist);
	}
	
	

}
