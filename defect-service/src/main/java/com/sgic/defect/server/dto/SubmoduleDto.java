package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubmoduleDto {
	@NotBlank(message = "Enter the Sub Module Name")
	private String submoduleName;
	private Long moduleId;
}
