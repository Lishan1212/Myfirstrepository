package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleAllocationGetDto {
	@NotBlank(message = "enter the full name")
	private String fullName;
	private boolean allocationStatus;
	

}
