package com.sgic.defect.server.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefectDto {
	@NotNull
	private String stepToRecreate;
	private String comment;
	private String attcahedment;

	@NotNull
	private Long assignedUserId;
	@NotNull
	private Long creativeUserId;
	@NotNull
	private String description;
	@NotNull
	private Long severityId;
	@NotNull
	private Long priorityId;
	@NotNull
	private Long subModuleId;
	@NotNull
	private Long defectTypeId;
	@NotNull
	private Long releaseId;
	@NotNull
	private Long statusId;
	@NotNull
	private Long projectId;

}
