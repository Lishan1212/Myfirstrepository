package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeverityIdDto {
	@NotBlank
	private String name;
	@NotBlank
	private String color;
	@NotBlank
	private String severityLevel;
	
}
