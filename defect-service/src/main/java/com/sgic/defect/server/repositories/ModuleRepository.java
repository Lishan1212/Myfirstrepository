package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Modules;
import com.sgic.defect.server.entities.Project;

public interface ModuleRepository extends JpaRepository<Modules, Long> {

	public List<Modules> findByModuleNameAndProject(String moduleName, Project project);
	
	public List<Modules> findAll(Sort sort);
	
	public boolean existsByModuleId(Long moduleId);
	
	public List<Modules> findByModuleName(String moduleName);

	public boolean existsByProject(Project project);
}
