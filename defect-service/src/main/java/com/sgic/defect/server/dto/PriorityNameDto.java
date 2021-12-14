package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriorityNameDto {

	@NotBlank
	private Long priorityId;
	@NotBlank
	private String name;

}
