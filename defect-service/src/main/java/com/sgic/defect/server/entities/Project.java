package com.sgic.defect.server.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Project")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long projectId;
	private String projectName;
	private String prefix;

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

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "projectTypeId")
	private ProjectType projectType;

}