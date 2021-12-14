package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Defect;
import com.sgic.defect.server.entities.DefectType;
import com.sgic.defect.server.entities.Project;

public interface DefectTypeRepository extends JpaRepository<DefectType, Long> {

	public List<DefectType> findByDefectTypeNameAndProject(String defectType, Project project);

	public boolean existsByDefectTypeId(Long defectTypeId);

	public List<DefectType> findByProject(Project project);


	public List<DefectType> findByDefectTypeName(String defectTypeName);

	public boolean existsByProject(Project project);

	public List<Defect> findByDefectTypeId(Long defectTypeId);

}
