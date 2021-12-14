package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleFindDto {
	private Long moduleId;
	@NotBlank
	private String moduleName;

	private Long projectId;
}
