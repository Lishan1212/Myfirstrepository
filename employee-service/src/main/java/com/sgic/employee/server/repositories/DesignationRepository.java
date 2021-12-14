package com.sgic.employee.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sgic.employee.server.entities.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long>{
	
	boolean existsByDesignationName(String designationName);
	
	boolean existsByDesignationId(Long designationId);
	
	public Designation findByDesignationNameIgnoreCase(String designationName);
	
	public Page<Designation> findAll(Pageable page);

	public List<Designation> findByDesignationNameStartsWithIgnoreCase(String designationName, Pageable page);

}
