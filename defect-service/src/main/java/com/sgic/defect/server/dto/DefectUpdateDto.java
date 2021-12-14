package com.sgic.defect.server.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefectUpdateDto {
	@NotNull
	private String stepToRecreate;
	private String comment;
	private String attcahedment;

	private String assignedUserId;
	@NotNull
	private Long creativeUserId;
	@NotNull
	private String description;
}
