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

import com.sgic.defect.server.dto.ReleasesDto;
import com.sgic.defect.server.dto.ReleasesFindAllDto;
import com.sgic.defect.server.dto.ReleasesNameDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.ReleasesService;

@RestController
public class ReleasesController {
	@Autowired
	ReleasesService releasesService;

	private final Logger logger = LoggerFactory.getLogger(ReleasesController.class);

	@PostMapping("/release")
	public ResponseEntity<Object> saveReleases(@Valid @RequestBody ReleasesDto releaseDto) {
		logger.info("Save release started");
		
		if(releasesService.existsProjectId(releaseDto.getProjectId())) {
			logger.info("Save release validation passed");
			releasesService.saveReleases(releaseDto);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}
		logger.info("Save release validation failed");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@GetMapping("/releases/{pageNumber}/{pageSize}")
	public List<ReleasesFindAllDto> findAllReleases(@PathVariable @RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue="release")String sortBy)
			
	{
		
		logger.info("FindAll releases started");

		return releasesService.findAllReleases(pageNumber, pageSize,sortBy);
	}
	
	@GetMapping("/releases/project/{id}")
    public ResponseEntity<Object> findAllReleasesByProjectId(@PathVariable Long id) {
        logger.info("FindAllReleasesByProjectId started");
        if(releasesService.existsProjectId(id)) {
          logger.info("findAllReleasesByProjectId validation passed");
          return ResponseEntity.ok(releasesService.findAllReleasesByProject(id));
        }
        logger.info("findAllReleasesByProjectId validation failed");
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
        responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
        return ResponseEntity.ok(responseDto);
    }

	@PutMapping("/release/{id}")
	public ResponseEntity<Object> updateReleases(@PathVariable Long id, @Valid @RequestBody ReleasesDto releaseDto) {
		logger.info("Update release started");

		if(releasesService.existsReleasesId(id)) {
			logger.info("Update release validation passed");
			releasesService.updateReleases(id, releaseDto);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}
		logger.info("Update release validation failed");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
		
	}

	@GetMapping("/release/{id}")
	public ResponseEntity<Object> findReleasesById(@PathVariable Long id) {
		logger.info("Find release byId started");
		
		return ResponseEntity.ok(releasesService.findReleasesById(id));
	}

	@GetMapping("/releases/names")
	public List<ReleasesNameDto> findReleasesNames() {
		logger.info("Find releases Names started");

		return releasesService.findReleasesNames();
	}
//	@GetMapping("/searchrelease")
//	public ResponseEntity<Object> searchRelease(@RequestParam("releaseName") String releaseName,
//			@RequestParam("releaseStatus") String releaseStatus, @RequestParam("releaseType") String releaseType,
//			@RequestParam("releaseSequence") String releaseSequence, @RequestParam("date") String date) {
//		logger.info("Search releases parameter passed");
//		List releaseList = releasesService.searchRelease(releaseName, releaseStatus, releaseType, releaseSequence,
//				date);
//
//		return ResponseEntity.ok(releaseList);
//
//	}
	
	@DeleteMapping("/deletereleases/{id}")
	public ResponseEntity<Object> deleteRelease(@PathVariable Long id) {
		if (releasesService.isexistById(id)) {
			if (!releasesService.existsByReleaseInRelease(id)) {
				releasesService.deleteDefectType(id);
				return ResponseEntity.ok("Release deleted");
			}
			return ResponseEntity.ok("Release not deleted");
		}
		return ResponseEntity.ok("sucessfully deleted  " + id);

	}
	
	
}
