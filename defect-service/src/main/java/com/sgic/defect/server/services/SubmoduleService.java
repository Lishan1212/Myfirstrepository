package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.SubmoduleDto;
import com.sgic.defect.server.dto.SubmoduleNameDto;

public interface SubmoduleService {

	public String saveSubmodule(SubmoduleDto submoduleDto);

	public String updateSubmdule(Long id, SubmoduleNameDto submoduleNameDto);

	public List<SubmoduleNameDto> findAllSubmoduleByModuleId(Long moduleId);

	public boolean validateeModuleDuplicateValue(SubmoduleDto submoduleDto);

	public boolean validateSubmoduleId(Long id);
	
	public boolean validateModuleId(Long moduleId);
	
	public SubmoduleNameDto findSubmoduleById(Long subModuleId);
	
	public void deleteBySubmoduleId(Long subModuleId);
	
	public boolean existSubmoduleInModuleAllocation(Long subModuleId);
}
