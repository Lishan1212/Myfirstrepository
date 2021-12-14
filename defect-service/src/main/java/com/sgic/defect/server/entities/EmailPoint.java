package com.sgic.defect.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@Table(name = "emailPoint")
public class EmailPoint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pointId;
	
	private String pointName;
	private boolean isActive;

}
