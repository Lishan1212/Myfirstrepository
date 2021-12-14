package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmailisactiveDto {

	// @NotBlank
	private ReleasesDto id;
	@NotBlank
	private boolean isactive;

}
