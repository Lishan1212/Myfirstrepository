package com.sgic.employee.server.services;

import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.employee.server.dto.DesignationDto;
import com.sgic.employee.server.entities.Designation;
import com.sgic.employee.server.entities.Employee;
import com.sgic.employee.server.repositories.DesignationRepository;
import com.sgic.employee.server.repositories.EmployeeRepository;

@Service
@Transactional
public class DesignationServiceImpl implements DesignationService {

	@Autowired
	private DesignationRepository designationRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private final Logger logger = LoggerFactory.getLogger(DesignationServiceImpl.class);

	@Override
	public String saveDesignation(DesignationDto designationDto) {

		logger.info("Save designation impl started");

		logger.info("Validation pass and save designation impl started");
		Designation designation = new Designation();
		designation.setDesignationName(designationDto.getDesignationName());
		designationRepository.save(designation);

		return ("Saved Successfully");

	}


	public void deleteDesignation(Long id) {

		logger.info("delete designation impl started");

		designationRepository.deleteById(id);
	}

	public String updateDesignation(Long id, DesignationDto designationDto) {

		logger.info("Update designation impl started");
		logger.info("Validation pass and update designation impl started");
		Designation designation = designationRepository.findById(id).get();
		designation.setDesignationName(designationDto.getDesignationName());

		designationRepository.save(designation);

		return ("Saved Successfully");

	}

	@Override
	public boolean validateError(DesignationDto designationDto) {
		if (!(designationRepository.existsByDesignationName(designationDto.getDesignationName()))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateErrorUpdate(DesignationDto designationDto) {
		if (!(designationRepository.existsByDesignationName(designationDto.getDesignationName()))) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean validateDesignationInEmployee(Long id) {
		if(employeeRepository.existsByDesignation(designationRepository.findById(id).get())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean existsDesignationId(Long id) {
		if(designationRepository.existsByDesignationId(id)) {
			return true;
		}
		return false;
	}

	
	@Override
	public List<DesignationDto> getAllDesignation(int pageNumber, int pageSize, String direction, String property, String designationName) {
		logger.info("Get all designations impl started");
		
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(Sort.Direction.fromString(direction), property));
		
		if(!designationName.isEmpty()) {
			logger.info("Designation name not blank");
			return designationRepository.findByDesignationNameStartsWithIgnoreCase(designationName, pageable).stream().map(this::convertDto).collect(Collectors.toList());
		}
		logger.info("Designation name is blank");
		return designationRepository.findAll(pageable).stream().map(this::convertDto).collect(Collectors.toList());
		
	}
	
	private DesignationDto convertDto(Designation designation) {

		logger.info("Designation convertDto impl started");
		DesignationDto designationDto = new DesignationDto();
		designationDto.setDesignationId(designation.getDesignationId());
		designationDto.setDesignationName(designation.getDesignationName());
		return designationDto;
	}
}
