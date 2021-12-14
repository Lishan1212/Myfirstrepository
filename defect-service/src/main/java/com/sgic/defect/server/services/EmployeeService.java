package com.sgic.defect.server.services;

import com.sgic.defect.server.dto.EmployeeDto;

public interface EmployeeService {
	public void saveEmployee(EmployeeDto employeeDto);

	public void updateEmployee(Long employeeId, EmployeeDto employeeDto);
}
