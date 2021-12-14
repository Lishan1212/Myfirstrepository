package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.ModuleAllocationGetDto;
import com.sgic.defect.server.dto.ModuleAllocationSaveDto;

public interface ModuleAllocationServices {

	public void savemoduleAllocation(ModuleAllocationSaveDto moduleAllocationDto);

	public void updateModuleAllocation(Long Id, ModuleAllocationGetDto moduleAllocationDto2);

	List<ModuleAllocationGetDto> getAllModuleAllocation(Integer pageNumber, Integer pageSize);

	Object findById(Long Id);

	public void moduleDeAllocation(Long moduleAllocationId);

	public boolean fullNameValidation(String fullName);

	public boolean moduleAllocationIdValidation(Long moduleAllocationId);

	public boolean isExistSubmodule(Long subModuleId);

	public boolean moduleAllocationValidation(Long subModuleId);

	List<ModuleAllocationGetDto> moduleAllocationsearch(String fullName);

}
