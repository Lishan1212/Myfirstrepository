package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AllDefectStatusDto {
	@NotBlank
	private ReleasesDto defectStatusId;
	@NotBlank
	private String defectStatusName;
	@NotBlank
	private String defectStatusColor;
}
