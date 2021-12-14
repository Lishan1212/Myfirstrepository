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

import com.sgic.defect.server.dto.Emaildto;
import com.sgic.defect.server.dto.ResponseDto;
import com.sgic.defect.server.enums.RestApiResponseStatus;
import com.sgic.defect.server.services.EmailSevices;

@RestController
public class EmailController {
	@Autowired
	EmailSevices emailSevices;

	final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@PostMapping("/email")
	public ResponseEntity<Object> saveEmail(@RequestBody Emaildto emaildto) {
		logger.info("Email details save start");
		ResponseDto responseDto = new ResponseDto();




		if (!emailSevices.errorvalidate(emaildto)) {
			
			emailSevices.saveEmail(emaildto);
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			logger.info("Save Email sucessfully !");
			return ResponseEntity.ok(responseDto);
		}

		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}


	
	
	@GetMapping("/emails/{pageNo}")
	public List<Emaildto>allEmail(@RequestParam(defaultValue = "0") Integer pageNo)
	{

		logger.info("get all the email details");
		return emailSevices.findAllemail(pageNo);
	}

	@PutMapping("/email/{id}")
	public ResponseEntity<Object> updateEmail(@PathVariable Long id, @RequestBody Emaildto emaildto) {
		logger.info("Email Details update Start!");
		ResponseDto responseDto = new ResponseDto();
		if (emailSevices.errorvalidate(emaildto)) {
			emailSevices.updateEmail(id, emaildto);
			logger.info("Update the email details");
			responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			return ResponseEntity.ok(responseDto);

		}
		responseDto.setCode(RestApiResponseStatus.ERROR.getCode());
		responseDto.setStatus(RestApiResponseStatus.ERROR.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@PutMapping("/isactive/{id}")
	public ResponseEntity<Object> saveIsActive(@PathVariable Long id) {

		logger.info("Active the Email");
		return ResponseEntity.ok(emailSevices.saveIsActive(id));
	}


	@DeleteMapping("/deleteemail/{id}")
	public ResponseEntity<Object> deleteemail(@PathVariable Long id) {
		logger.info("Delete email started successfully!");
		ResponseDto responseDto = new ResponseDto();
		emailSevices.deleteEmail(id);

		responseDto.setCode(RestApiResponseStatus.OK.getCode());
		responseDto.setStatus(RestApiResponseStatus.OK.getStatus());
		logger.info(" sucessfully deleted!");
		return ResponseEntity.ok(responseDto);
		
	}
	@GetMapping("/searchemail")
	public ResponseEntity<Object> searchEmail(@RequestParam("emailId") String emailId,
			@RequestParam("protocol") Integer protocol, @RequestParam("hostid") Long hostid,
			@RequestParam("portnumber") Integer portnumber) {

		List emailList = emailSevices.searchEmail(emailId, protocol, hostid, portnumber);
		logger.info("email request Parameter passed");
		return ResponseEntity.ok(emailList);

	}

}
