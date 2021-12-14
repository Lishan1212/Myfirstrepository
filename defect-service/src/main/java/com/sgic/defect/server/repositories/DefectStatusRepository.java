package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.DefectStatus;
import com.sgic.defect.server.entities.Project;

public interface DefectStatusRepository extends JpaRepository<DefectStatus, Long> {

	public List<DefectStatus> findByDefectStatusIdAndProject(Long defectStatusId, Project project);

	public boolean existsByDefectStatusId(Long defectStatusId);

	public List<DefectStatus> findAllByProject(Project project);

	public boolean existsByDefectStatusName(String Name);

	public Page<DefectStatus> findAll(Pageable page);

	public List<DefectStatus> findByDefectStatusName(String defectStatusName);

	public List<DefectStatus> findByDefectStatusNameAndProject(String defectStatusName, Project project);

	public List<DefectStatus> findByDefectStatusColor(String defectStatusColor);


	public List<DefectStatus> findByDefectStatusId(Long defectStatusId);

	boolean existsByDefectStatusColor(String defectStatusColor);
	

	public boolean existsByProject(Project project);

}
