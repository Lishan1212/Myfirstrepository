package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.ModuleDto;
import com.sgic.defect.server.dto.ModuleFindDto;
import com.sgic.defect.server.dto.ModuleNameDto;

public interface ModuleService {

	public String saveModule(ModuleDto moduleDto);

	public String updateModule(Long id, ModuleDto moduleDto);

	public List<ModuleNameDto> findAllModule();

	public List<ModuleNameDto> findAllPagination(Integer pageNumber);
	
	public List<ModuleNameDto> moduleSorting(String direction, String properties);

	public ModuleFindDto findModuleById(Long moduleId);

	public boolean validateProjectId(ModuleDto moduleDto);

	public boolean validateDuplicateModule(ModuleDto moduleDto);

	public boolean validateModuleId(Long moduleId);

	public List<ModuleFindDto> moduleSearch(String moduleName);
	
	public void deleteModule(Long moduleId);
	
	public boolean validateModuleInSubmodule(Long moduleId);

//	List<ModuleFindDto> moduleSearch(String moduleName);

}
