package com.sgic.defect.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DefectFindAllDto {

	private Long defectId;
	private SeverityFindDto severityFindDto;
	private PriorityFindDto priorityFindDto;
	private String stepToRecreate;
	private String comment;
	private String attcahedment;
	private Long assignedUserId;
	private Long creativeUserId;
	private SubmoduleNameDto submoduleNameDto;
	private DefectTypeFindDto defectTypeFindDto;
	private ReleasesFindAllDto releasesFindAllDto;
	private String description;
	private DefectStatusFindAllDto defectStatusFindAllDto;

}
