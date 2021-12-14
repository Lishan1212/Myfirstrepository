package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectAllocationDto {

	private String fullName;

	private Long percentage;

	private Long projectId;
	@NotBlank
	private String roleName;
	private Long roleId;
	private Long employeeId;
	// private String ActiveStatus;

}
