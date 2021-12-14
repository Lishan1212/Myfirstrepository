package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.ModuleAllocation;
import com.sgic.defect.server.entities.Priority;
import com.sgic.defect.server.entities.Submodule;

public interface ModuleAllocationRepository extends JpaRepository<ModuleAllocation, Long> {

	public List<ModuleAllocation> findByfullNameAndSubmodule(String fullName, Submodule submodule);

	public boolean existsByFullName(String fullName);

	public boolean existsBymoduleAllocationId(Long moduleAllocationId);

	public boolean existsBySubmodule(Submodule submodule);

	public List<ModuleAllocation> findByAllocationStatusAndSubmodule(boolean allocationStatus, Submodule submodule);
	
	
	public List<ModuleAllocation> findByFullName(String fullName);
	
	public ModuleAllocation findBySubmodule(Submodule submodule);

}
	