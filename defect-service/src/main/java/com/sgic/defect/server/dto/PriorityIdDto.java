package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriorityIdDto {
	@NotBlank
	private String name;
	@NotBlank
	private String color;
	@NotBlank
	private String priorityLevel;

}
