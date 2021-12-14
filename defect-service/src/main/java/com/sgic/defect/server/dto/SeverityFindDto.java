package com.sgic.defect.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeverityFindDto {
	private String name;
	private Long severityId;
	private String color;
	private String severityLevel;

}
