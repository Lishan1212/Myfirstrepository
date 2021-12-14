package com.sgic.employee.server.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.sgic.employee.server.entities.Designation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeDto {

	private Long employeeId;
	
	@NotNull(message = "The FirstName is required.")
	@Column(unique = true)
	@Pattern(regexp = "^[A-Za-z]+$",message = "")
	private String firstName;
	
	@NotEmpty(message = "The LastName is required.")
	@Column(unique = true)
	@Pattern(regexp = "^[A-Za-z]+$",message = "")
	private String lastName;
	
	@NotEmpty(message = "The Gender is required.")
	@Pattern(regexp = "^[A-Za-z]+$",message = "")
	private String gender;
	
	@NotEmpty(message = "The email address is required.")
	@Email
	private String emailId;
	
	@Pattern(regexp = "(^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$)")
	private String phoneNo;
	
	private Long designationId;
	
}
