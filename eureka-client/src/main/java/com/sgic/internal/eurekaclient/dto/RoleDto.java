package com.sgic.internal.eurekaclient.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
	@NotNull
	@Pattern(regexp = "^[A-Za-z]+$",message = "")
	private String roleName;

}
