package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DefectStatusDto {
	@NotBlank
	private String defectStatusName;
	@NotBlank
	private String defectStatusColor;
	
	private Long projectId;

	
}
