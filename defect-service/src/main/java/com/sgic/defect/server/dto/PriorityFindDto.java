package com.sgic.defect.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriorityFindDto {

	private Long priorityId;
	private String name;
	private String color;
	private String priorityLevel;
}
