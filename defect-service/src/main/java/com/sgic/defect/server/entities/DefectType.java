
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
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="defecttype")

public class DefectType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long defectTypeId;
	@NotBlank
	private String defectTypeName;
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="projectId")
	private Project project;
	

}
