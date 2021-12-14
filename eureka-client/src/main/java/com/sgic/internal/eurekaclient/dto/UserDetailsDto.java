package com.sgic.internal.eurekaclient.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter



public class UserDetailsDto {
	private Long userId;
	@NotBlank
	private String userName;
	@javax.validation.constraints.Email
	 String userEmail;
	@NotBlank
	private String password;
}
