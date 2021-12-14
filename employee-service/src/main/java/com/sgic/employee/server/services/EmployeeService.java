package com.sgic.employee.server.services;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.sgic.employee.server.dto.EmployeeDto;
import com.sgic.employee.server.dto.EmployeeFindAllDto;
import com.sgic.employee.server.dto.EmployeeListByIdDto;
import com.sgic.employee.server.dto.EmployeeNameDto;

public interface EmployeeService {

  public String saveEmployee(EmployeeDto employeeDto);

  public String updateEmployee(Long id, EmployeeDto employeeDto);

  public EmployeeNameDto getEmployeeNameById(Long id);

  public EmployeeListByIdDto findEmployeeById(Long id);

  public void importEmployee(MultipartFile file);

  public boolean checkDublicateFeildsEmailIdAndPhoneNo(EmployeeDto employeeDto);

  public boolean checkExistsEmployeeId(EmployeeDto employeeDto);

  public void deleteEmployee(Long id);

  public boolean existsEmployeeId(Long id);
  
  public List<EmployeeFindAllDto> findAllEmployeeDetails(int pageNumber,int pageSize, String direction, String property, Long employeeId, String firstName, String lastName, String gender, String emailId, String phoneNo, String designationName);
}
