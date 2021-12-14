package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReleasesFindAllDto {
	
	private Long releaseId;
	@NotBlank
	private String releaseName;
	@NotBlank
	private String releaseStatus;
	@NotBlank
	private String releaseType;
	@NotBlank
	private String releaseSequence;
	@NotBlank
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String date;
}
