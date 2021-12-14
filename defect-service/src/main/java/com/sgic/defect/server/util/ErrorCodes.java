package com.sgic.defect.server.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@PropertySource("classpath:ErrorMessages.properties")
public class ErrorCodes {

	@Value("${validation.employee.notExists}")
	private Integer employeeNotExist;

	@Value("${validation.email.alreadyExist}")
	private Integer emailAlreadyExist;

	
	
	

}
