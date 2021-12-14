package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmoduleFindDto {

	private Long submoduleId;
	@NotBlank(message = " Enter The Sub Module Name")
	private String submoduleName;

	private Long moduleId;
}
