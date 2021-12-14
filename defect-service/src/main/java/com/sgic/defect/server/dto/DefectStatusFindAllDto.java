package com.sgic.defect.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DefectStatusFindAllDto {

	private Long defectStatusId;
	private String statusName;
	private String statusColor;
}
