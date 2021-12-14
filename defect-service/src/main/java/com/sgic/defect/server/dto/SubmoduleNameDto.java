package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubmoduleNameDto {

	private Long submoduleId;
	@NotBlank(message = " Enter The Sub Module Name")
	private String submoduleName;
}
