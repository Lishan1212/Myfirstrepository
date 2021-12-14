package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import com.sgic.defect.server.entities.Defect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeverityDto {
	@NotBlank
	private String name;
	@NotBlank
	private String color;
	@NotBlank
	private String severityLevel;
	@NotBlank
	private Long projectId;
	private Defect defect;
}
