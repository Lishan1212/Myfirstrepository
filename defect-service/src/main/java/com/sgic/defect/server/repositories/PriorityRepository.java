package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Priority;
import com.sgic.defect.server.entities.Project;

public interface PriorityRepository extends JpaRepository<Priority, Long> {

	public List<Priority> findByPriorityNameAndProject(String priorityName, Project project);

	public List<Priority> findByPriorityColorAndProject(String priorityColor, Project Project);

	public List<Priority> findByProject(Project project);

	public Page<Priority> findAll(Pageable page);

	public boolean existsByPriorityId(Long priorityId);

	public List<Priority> findByPriorityName(String priorityName);

	public List<Priority> findByPriorityColor(String priorityColor);

	public List<Priority> findByPriorityLevel(String priorityLevel);

	public Object findByPriorityId(String priorityId);
	
	public boolean existsByProject(Project project);
	

}
