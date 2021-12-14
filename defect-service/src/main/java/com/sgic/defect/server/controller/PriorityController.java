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

import com.sgic.defect.server.dto.PriorityDto;
import com.sgic.defect.server.dto.PriorityIdDto;
import com.sgic.defect.server.dto.PriorityNameDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.PriorityService;

@RestController
public class PriorityController {
	ResponseDto responseDto = new ResponseDto();

	@Autowired
	PriorityService priorityService;

	private final Logger logger = LoggerFactory.getLogger(PriorityController.class);

	@GetMapping("/priorities")
	public List<PriorityIdDto> findAllPriority() {
		logger.info("get all priority started");

		return priorityService.findAllPriority();
	}

	@PostMapping("/priority")
	public ResponseEntity<Object> savePirority(@Valid @RequestBody PriorityDto priorityDto) {
		logger.info("save priority started");
		// ResponseDto responseDto=new ResponseDto();
		if (priorityService.validateduplicate(priorityDto)) {
			priorityService.savePriority(priorityDto);
			logger.info("Save priority sucessfully !", priorityDto);
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}
		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/priority/{id}")
	public ResponseEntity<Object> updatePriority(@PathVariable Long id, @Valid @RequestBody PriorityDto priorityDto) {
		logger.info("update priority started");
		if (priorityService.validateduplicate(priorityDto)) {

			priorityService.updatePriority(id, priorityDto);
			logger.info("Update sucessfully !", priorityDto);
			responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}

		responseDto.setCode(RestApiResponseStatus.ERROR.getCode());
		responseDto.setStatus(RestApiResponseStatus.ERROR.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@GetMapping("/priorityname/{pageNumber}/{pageSize}")
	public List<PriorityNameDto> allNamePirority(@PathVariable int pageNumber, @PathVariable int pageSize,
			Pageable pageable) {
		logger.info("get all defect name,ID started");
		// PriorityNameDto priorityNameDto = new PriorityNameDto();
		return priorityService.findAllNamePriority(pageNumber, pageSize);

	}

	@GetMapping("/PriorityProject/{id}")
	public ResponseEntity<Object> getAllPriorityByProject(@PathVariable Long id) {

		if (priorityService.validateprojectid(id)) {
			logger.info("get Stared for the Details Based on project id ");
			return ResponseEntity.ok(priorityService.getAllPriorityByProject(id));
		}

		responseDto.setCode(RestApiResponseStatus.ERROR.getCode());
		responseDto.setStatus(RestApiResponseStatus.ERROR.getStatus());
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/searchpriority")
	public ResponseEntity<Object> Searchpriority(@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String color,
			@RequestParam(required = false, defaultValue = "priorityLevel") String priorityLevel) {
		logger.info("Search started");
		List<PriorityDto> prioritynamelist = priorityService.prioritySerachByName(name, color, priorityLevel);
		return ResponseEntity.ok(prioritynamelist);
	}


	@DeleteMapping("/deletepriority/{id}")
	public ResponseEntity<Object>deletepriority(@PathVariable Long id)
	{
		if(priorityService.isExistById(id))
		{
			if(!priorityService.existsByPriorityInPriority(id))
			{

				logger.info("Priority is deleted");
				priorityService.deletePriority(id);
				//logger.info("")
				return ResponseEntity.ok("priority Deleted");

			}
			logger.info("Prioriry is not deleted");
			return ResponseEntity.ok("Priority is not deleted");
		}
		logger.info("data are scuessfully deleated");
		return ResponseEntity.ok("Sucessfully deleted");
	}

}
