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

import com.sgic.defect.server.dto.EmployeeDto;
import com.sgic.defect.server.services.EmployeeService;

@RestController
public class EmployeeController {
	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	EmployeeService employeeService;

	@PostMapping("/employee")
	public ResponseEntity<Object> saveEmployee(@RequestBody EmployeeDto employeeDto) {
		logger.info("employee save start");
		employeeService.saveEmployee(employeeDto);
		return ResponseEntity.ok("save successfully");
	}

	@PutMapping("/employee/{employeeId}")
	public ResponseEntity<Object> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDto employeeDto) {
		logger.info("employee update start", employeeId);
		employeeService.updateEmployee(employeeId, employeeDto);
		return ResponseEntity.ok("update successfuly");
	}
}
