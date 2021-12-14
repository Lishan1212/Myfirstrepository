package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Modules;
import com.sgic.defect.server.entities.Submodule;

public interface SubmoduleRepository extends JpaRepository<Submodule, Long> {

	public Submodule findBySubModuleName(String subModuleName);

	public List<Submodule> findBySubModuleNameAndModule(String subModuleName, Modules module);

	public List<Submodule> findByModule(Modules module);

	public boolean existsBySubModuleId(Long subModuleId);

	public boolean existsByModule(Modules module);

	Object findBySubModuleId(String subModuleId);
}
