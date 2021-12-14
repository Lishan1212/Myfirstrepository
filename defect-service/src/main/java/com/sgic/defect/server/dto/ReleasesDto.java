package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReleasesDto {

	@NotBlank
	private String releaseName;
	@NotBlank
	private String releaseStatus;
	@NotBlank
	private String releaseType;

	private String releaseSequence;
	private Long projectId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String date;
}
