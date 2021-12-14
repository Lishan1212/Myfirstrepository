package com.sgic.defect.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.defect.server.dto.EmailPointIsActiveDto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.EmailPointService;

@RestController
public class EmailPointController {

	final Logger logger = LoggerFactory.getLogger(EmailPointController.class);
	@Autowired
	EmailPointService emailPointService;

	@PostMapping("/emailpoint")
	public ResponseEntity<Object> saveEmailPoint(@RequestBody EmailPointIsActiveDto emailPointIsActiveDto) {
		logger.info("Emailpoints  save start");
		ResponseDto responseDto = new ResponseDto();
		if (!emailPointService.errorvalidate(emailPointIsActiveDto)) {
			emailPointService.saveEmailPoints(emailPointIsActiveDto);
			logger.info("Save Email sucessfully !", emailPointIsActiveDto);
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}

		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@PutMapping("/isactiveemailpoint/{id}")
	public ResponseEntity<Object> updateIsActive(@PathVariable Long id) {

		logger.info("Active the Email point");
		return ResponseEntity.ok(emailPointService.updateIsActive(id));
	}

}
