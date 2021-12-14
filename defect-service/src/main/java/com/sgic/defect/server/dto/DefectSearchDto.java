package com.sgic.defect.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DefectSearchDto {

	private Long defectId;

	private Long AssignedUserId;

	private Long creativeUserId;

	private String description;

	private Long severityId;

	private Long priorityId;

	private Long subModuleId;

	private Long defectTypeId;

	private Long releaseId;

	private Long statusId;

	private Long projectId;

}
