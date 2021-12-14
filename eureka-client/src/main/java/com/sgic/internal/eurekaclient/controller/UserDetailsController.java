package com.sgic.internal.eurekaclient.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.internal.eurekaclient.dto.ResponseDto;
import com.sgic.internal.eurekaclient.dto.UserDetailsDto;
import com.sgic.internal.eurekaclient.enums.RestApiResponseStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.sgic.internal.eurekaclient.services.UserDetailsService;

@RestController
public class UserDetailsController {
	@Autowired
	UserDetailsService userDetailsService;

	private final Logger Logger = LoggerFactory.getLogger(UserDetailsController.class);

	@GetMapping("/userDetails")
	public List<UserDetailsDto> findAllPriority() {
		return userDetailsService.findAllUserDetails();
	}

	@PostMapping("/userDetail")
	public ResponseEntity<Object> saveModule(@Valid @RequestBody UserDetailsDto userDetailsDto) {
		{
			Logger.info("user details save started in controller");
			userDetailsService.saveUserDetails(userDetailsDto);
			Logger.info("Save user details success");
			return ResponseEntity.ok("user details saved sucessfully!");
		}
	}


	@PutMapping("updateuserdetails/{id}")
	public ResponseEntity<Object>updateuserDetails(@PathVariable long id,@RequestBody UserDetailsDto userDetailsDto)
	{
		ResponseDto responseDto = new ResponseDto();
		if(userDetailsService.validateUserId(id))
		{
			Logger.info("update Started .........");
			userDetailsService.updateuserDetails(id, userDetailsDto);
			responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}
			responseDto.setCode(RestApiResponseStatus.ERROR.getCode());
			responseDto.setStatus(RestApiResponseStatus.ERROR.getStatus());
			return ResponseEntity.ok(responseDto);
	}


	@GetMapping("/userdetails/{userId}")
	public ResponseEntity<Object>findUserDetailsById(@Valid @PathVariable Long userId) {
		if (userDetailsService.validateUserId(userId)) {
			Logger.info("User details find by id started in controller");
              return ResponseEntity.ok(userDetailsService.findUserDetailsById(userId));
			}
		Logger.info("Module id not exists");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus .FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}
	


}


