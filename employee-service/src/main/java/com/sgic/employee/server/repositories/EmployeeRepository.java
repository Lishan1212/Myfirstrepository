package com.sgic.employee.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.employee.server.entities.Designation;
import com.sgic.employee.server.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public boolean existsByEmailId(String emailId);
	
	public boolean existsByPhoneNo(String phoneNo);
	
	public boolean existsByEmployeeId(Long employeeId);

	public boolean existsByDesignation(Designation designation);
	
	public Page<Employee> findAll(Pageable page);
	
	List<Employee> findByEmployeeId(Long employeeId, Pageable page);
	
	List<Employee> findEmployeeByFirstNameIgnoreCase(String firstName, Pageable page);
	
	List<Employee> findEmployeeByLastNameIgnoreCase(String lastName, Pageable page);
	
	List<Employee> findEmployeeByGenderIgnoreCase(String gender, Pageable page);
	
	List<Employee> findEmployeeByEmailId(String emailId, Pageable page);
	
	List<Employee> findEmployeeByPhoneNo(String phoneNo, Pageable page);
	
	List<Employee> findEmployeeByDesignation(Designation designation, Pageable page);

	
 
}
