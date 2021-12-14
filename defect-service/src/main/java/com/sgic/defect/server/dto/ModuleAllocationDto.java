package com.sgic.defect.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModuleAllocationDto {

	private Long moduleAllocationId;
	private String fullname;
	private SubmoduleNameDto submoduleNameDto;
	private ModuleNameDto moduleNameDto;
}
