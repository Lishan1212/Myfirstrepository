package com.sgic.employee.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeErrorDto {
	
	private String status;

	private Integer code;


}
