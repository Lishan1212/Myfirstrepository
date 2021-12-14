package com.sgic.defect.server.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailPointIsActiveDto {
	
		
		@Column(unique = true)
		private Long pointId;
		
		private boolean isActive;
		@NotBlank
		private String pointName;

}
