package com.sgic.internal.eurekaclient.controller;

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

import com.sgic.internal.eurekaclient.dto.ResponseDto;
import com.sgic.internal.eurekaclient.dto.RoleDto;
import com.sgic.internal.eurekaclient.enums.RestApiResponseStatus;
import com.sgic.internal.eurekaclient.services.RoleService;

@RestController
public class RoleConroller {
	@Autowired
	RoleService roleService;
	private final Logger logger = LoggerFactory.getLogger(RoleConroller.class);

	@PostMapping("/role")
	public ResponseEntity<Object> saveRole(@Valid @RequestBody RoleDto roleDto) {
		logger.info("Role save started");
		ResponseDto responseDto = new ResponseDto();
		if(roleService.existsRoleName(roleDto)) {
			roleService.saveRole(roleDto);
			
			responseDto.setCode(RestApiResponseStatus.CREATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}
		
		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}
	
	@GetMapping("/role/{id}")
	public ResponseEntity<Object> findRoleById(@PathVariable Long id) {
		logger.info("Role findRoleById started");
		if(roleService.existsRoleId(id)) {
			return ResponseEntity.ok(roleService.findRoleById(id));
		}
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@PutMapping("/role/{id}")
	public ResponseEntity<Object> updateRole(@PathVariable Long id, @Valid @RequestBody RoleDto roleDto) {
		logger.info("Role update started");
		ResponseDto responseDto = new ResponseDto();
		if(roleService.existsRoleId(id) && roleService.existsRoleName(roleDto)) {
			roleService.updateRole(id, roleDto);
			responseDto.setCode(RestApiResponseStatus.UPDATED.getCode());
			responseDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
			return ResponseEntity.ok(responseDto);
		}
		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);

	}

	@DeleteMapping("/role/{id}")
	public ResponseEntity<Object> deleteRole(@PathVariable Long id) {
		logger.info("Role delete started");
		ResponseDto responseDto = new ResponseDto();
		if(roleService.existsRoleId(id) && roleService.existsRoleInUserRole(id)) {
			roleService.deleteRole(id);
			responseDto.setCode(RestApiResponseStatus.DELETED.getCode());
			responseDto.setStatus(RestApiResponseStatus.DELETED.getStatus());
			return ResponseEntity.ok(responseDto);
		}
		responseDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		responseDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(responseDto);
	}
	
	@GetMapping("/roles")
	public List<RoleDto> findAllRole(){
		
		logger.info("Get all roles started");
		return roleService.findAllRole();
	}
}
