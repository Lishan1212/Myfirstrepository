package com.sgic.employee.server.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesignationDto {

	private Long designationId;
	@NotBlank
	@Pattern(regexp = "^[A-Za-z]+$",message = "")
	private String designationName;
	

}
