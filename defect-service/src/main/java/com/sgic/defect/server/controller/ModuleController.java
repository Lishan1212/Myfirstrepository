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

import com.sgic.defect.server.dto.DefectTypeDto;
import com.sgic.defect.server.dto.ModuleDto;
import com.sgic.defect.server.dto.ModuleFindDto;
import com.sgic.defect.server.dto.ModuleNameDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.repositories.ModuleRepository;
import com.sgic.defect.server.services.ModuleService;

@RestController
public class ModuleController {
	@Autowired
	ModuleService moduleService;

	final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	@PostMapping("/module")
	public ResponseEntity<Object> saveModule(@Valid @RequestBody ModuleDto moduleDto) {
		if (moduleService.validateProjectId(moduleDto) && moduleService.validateDuplicateModule(moduleDto)) {
			moduleService.saveModule(moduleDto);
			logger.info("Module save started in controller");
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			logger.info("Save Module success");
			return ResponseEntity.ok(responseDto);
		}

		logger.info("Save Module Failed");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.EXISTS.getCode());
		responseDto.setStatus(RestApiResponseStatus.EXISTS.getStatus());
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/module/{id}")
	public ResponseEntity<Object> updateModule(@PathVariable Long id, @Valid @RequestBody ModuleDto moduleDto) {
		if (moduleService.validateModuleId(id)) {
			logger.info("Module update started in controller");
			if (moduleService.validateDuplicateModule(moduleDto)) {
				logger.info("Module not have duplicate");
				moduleService.updateModule(id, moduleDto);
				ResponseDto responseDto = new ResponseDto();
				responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
				responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
				logger.info("update module success");
				return ResponseEntity.ok(responseDto);
			}
			logger.info("Update module Failed - duplicate value");
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.EXISTS.getCode());
			responseDto.setStatus(RestApiResponseStatus.EXISTS.getStatus());
			return ResponseEntity.ok(responseDto);

		}
		logger.info("Update module Failed");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@GetMapping("/modules")
	public List<ModuleNameDto> findAllModule() {
		logger.info("Module find all started in controller");
		return moduleService.findAllModule();
	}

	@GetMapping("/module/{moduleId}")
	public ResponseEntity<Object> findModuleById(@Valid @PathVariable Long moduleId) {
		if (moduleService.validateModuleId(moduleId)) {
			logger.info("Module find by id started in controller");

			return ResponseEntity.ok(moduleService.findModuleById(moduleId));
		}
		logger.info("Module id not exists");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}
	
	@GetMapping("/modulepage")
	public ResponseEntity<Object> findAllPagination(@RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
		logger.info("pagination started in module controller");
		return ResponseEntity.ok(moduleService.findAllPagination(pageNumber));
		
	}
	
	@GetMapping("/modulesort")
	public ResponseEntity<Object> moduleSorting(@RequestParam(required = false, defaultValue = "asc") String direction, @RequestParam String properties) {
		logger.info("sorting started in module controller");
		return ResponseEntity.ok(moduleService.moduleSorting(direction, properties));
		
	}
	@GetMapping("/moduleesearch")
	public ResponseEntity<Object> Searchdefecttype(@RequestParam(required = false, defaultValue ="" ) String moduleName)
	{
		logger.info("Search started");
		List<ModuleFindDto> modulelist=moduleService.moduleSearch(moduleName);
		return ResponseEntity.ok(modulelist);
	}
	
	
	@DeleteMapping("/module/{moduleId}")
	public ResponseEntity<Object> deleteModule(@PathVariable Long moduleId){
		
		if(!moduleService.validateModuleInSubmodule(moduleId)) {
			moduleService.deleteModule(moduleId);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.OK.getCode());
			responseDto.setStatus(RestApiResponseStatus.OK.getStatus());
			logger.info("update module success");
			return ResponseEntity.ok(responseDto);
		}		
		
		logger.info("Module id not exists");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}
	

}
