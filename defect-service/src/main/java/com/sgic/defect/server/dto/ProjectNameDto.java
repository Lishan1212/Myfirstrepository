package com.sgic.defect.server.dto;

import javax.validation.constraints.NotBlank;

//import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class ProjectNameDto {

	
	private Long projectId;
	@NotBlank
	private String projectName;
//	private String prefix;
//	private Long projectTypeId;
//	@JsonFormat(pattern = "yyyy-MM-dd")
//	private String startDate;
//	@JsonFormat(pattern = "yyyy-MM-dd")
//	private String endDate;
//	//private Long typeId;
//	private String statusName;
//	private String description;
//	private String clientName;
//	private String clientAddress;
//	private String contactPersonName;
//	private String contactPersonMobileNumber;
//	private String contactPersonEmailId;

	
	
	
	
}