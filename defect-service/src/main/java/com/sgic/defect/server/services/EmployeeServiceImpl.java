package com.sgic.defect.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.EmployeeDto;
import com.sgic.defect.server.entities.Employee;
import com.sgic.defect.server.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public void saveEmployee(EmployeeDto employeeDto) {
		logger.info("employee save start");
		Employee employee = new Employee();
		employee.setFirstName(employeeDto.getFirstName());
		employee.setLastName(employeeDto.getLastName());
		employee.setGender(employeeDto.getGender());
		employee.setEmailId(employeeDto.getEmailId());
		employee.setMobileNumber(employeeDto.getMobileNumber());
		employeeRepository.save(employee);

	}

	@Override
	public void updateEmployee(Long employeeId, EmployeeDto employeeDto) {
		logger.info("employeeupdate start", employeeId);
		Employee employee = employeeRepository.findById(employeeId).get();
		employee.setFirstName(employeeDto.getFirstName());
		employee.setLastName(employeeDto.getLastName());
		employee.setGender(employeeDto.getGender());
		employee.setEmailId(employeeDto.getEmailId());
		employee.setMobileNumber(employeeDto.getMobileNumber());
		employeeRepository.save(employee);

	}

}
