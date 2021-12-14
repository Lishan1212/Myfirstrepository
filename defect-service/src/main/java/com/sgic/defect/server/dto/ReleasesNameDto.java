package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReleasesNameDto {
	private Long releaseId;
	@NotBlank
	private String releaseName;

}
