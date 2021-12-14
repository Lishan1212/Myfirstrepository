
package com.sgic.defect.server.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ProjectAllocation")
public class ProjectAllocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectAllocationId;
	private String fullName;
	private Long percentage;
	private String roleName;
	private Long roleId;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "projectId")
	private Project project;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeId")
	private Employee employee;

}
