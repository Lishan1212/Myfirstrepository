package com.sgic.employee.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesignationNameDto {

	private String designationName;

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

}
