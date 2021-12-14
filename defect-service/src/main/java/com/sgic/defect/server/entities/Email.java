package com.sgic.defect.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Email {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@javax.validation.constraints.Email
	private String emailId;

	private String password;

	private Integer protocol;

	private Long hostid;

	private int portnumber;

	private boolean isactive;

}
