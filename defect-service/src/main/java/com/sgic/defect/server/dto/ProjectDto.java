package com.sgic.defect.server.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDto {
	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "^[A-Za-z]+$", message = "Enter the project name")
	private String projectName;
	@NotNull
	@Pattern(regexp = "(^[A-Z]+$)", message = "Enter in Uppercase")
	private String prefix;
	@NotNull
	private Long projectTypeId;
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String startDate;
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String endDate;
	@NotNull
	@Pattern(regexp = "^[A-Za-z]+$", message = "Enter  status name")
	private String statusName;
	@Pattern(regexp = "^[A-Za-z]+$", message = "Enter  discription")
	private String description;
	@Pattern(regexp = "^[A-Za-z]+$", message = "Enter in client name")
	private String clientName;
	@Pattern(regexp = "^[A-Za-z]+$", message = "Enter in client address")
	private String clientAddress;
	@Pattern(regexp = "^[A-Za-z]+$", message = "Enter contact person name")
	private String contactPersonName;
	@Pattern(regexp = "(^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$)", message = "Enter contact person Mobile number")
	private String contactPersonMobileNumber;
	@Email
	@Pattern(regexp = "^(.+)@(\\S+)$", message = "Enter contact person email id")
	private String contactPersonEmailId;
	private ProjectTypeDto projectTypeDto;

}
