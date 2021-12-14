package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProjectTypeDto {

	@NotBlank
	private String projectTypeName;

}
