package com.sgic.defect.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectFindAllDto {
	private String projectName;
	private String prefix;
	private Long projectTypeId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String startDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String endDate;
	private String statusName;
	private String description;
	private String clientName;
	private String clientAddress;
	private String contactPersonName;
	private String contactPersonMobileNumber;
	private String contactPersonEmailId;

}