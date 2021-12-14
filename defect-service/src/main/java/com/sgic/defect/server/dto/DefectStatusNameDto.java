package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class DefectStatusNameDto {
	@NotBlank
	private Long defectStatusId;
	@NotBlank
	private String name;
}
