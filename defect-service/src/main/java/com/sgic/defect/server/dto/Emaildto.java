package com.sgic.defect.server.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Emaildto {
	@NotBlank
	@Column(unique = true)
	private String emailId;
	@NotBlank
	private String password;
	@NotBlank
	private Integer protocol;
	@NotBlank
	private Long hostid;
	@NotBlank
	private int portnumber;

}
