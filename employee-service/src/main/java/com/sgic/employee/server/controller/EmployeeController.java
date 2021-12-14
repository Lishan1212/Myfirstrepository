package com.sgic.employee.server.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sgic.employee.server.dto.EmployeeDto;
import com.sgic.employee.server.dto.EmployeeErrorDto;
import com.sgic.employee.server.dto.EmployeeFindAllDto;
import com.sgic.employee.server.dto.EmployeeListByIdDto;
import com.sgic.employee.server.dto.EmployeeNameDto;
import com.sgic.employee.server.entities.Employee;
import com.sgic.employee.server.enums.RestApiResponseStatus;
import com.sgic.employee.server.export.EmployeeExcelExport;
import com.sgic.employee.server.repositories.EmployeeRepository;
import com.sgic.employee.server.services.EmployeeService;
import com.sgic.employee.server.util.ErrorCodes;

@RestController
@ResponseBody
public class EmployeeController {

  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private EmployeeService employeeService;
  @Autowired
  ErrorCodes errorMessages;

  EmployeeErrorDto employeeErrorDto = new EmployeeErrorDto();

  private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

  @PostMapping("/employee")
  public ResponseEntity<Object> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
    logger.info("Save employee started");
    if (employeeService.checkDublicateFeildsEmailIdAndPhoneNo(employeeDto)) {
      employeeService.saveEmployee(employeeDto);
      employeeErrorDto.setCode(RestApiResponseStatus.CREATED.getCode());
      employeeErrorDto.setStatus(RestApiResponseStatus.CREATED.getStatus());
      logger.info("created successfully");
      return ResponseEntity.ok(employeeErrorDto);

    }

    employeeErrorDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
    employeeErrorDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
    logger.info("Employee save validation Failed");
    return ResponseEntity.ok(employeeErrorDto);

  }

  @PutMapping("/employee/{employeeId}")
  public ResponseEntity<Object> updateEmployee(@PathVariable Long id,
      @Valid @RequestBody EmployeeDto employeeDto) {
    logger.info("Update employee started");
    if ((employeeService.checkExistsEmployeeId(employeeDto))) {
      if ((employeeService.checkDublicateFeildsEmailIdAndPhoneNo(employeeDto))) {
        employeeService.updateEmployee(id, employeeDto);
        employeeErrorDto.setCode(RestApiResponseStatus.UPDATED.getCode());
        employeeErrorDto.setStatus(RestApiResponseStatus.UPDATED.getStatus());
        logger.info("Update employee successfully");
        return ResponseEntity.ok(employeeErrorDto);
      }
      logger.info("Validation Failure in Update");
      employeeErrorDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
      employeeErrorDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
      return ResponseEntity.ok(employeeErrorDto);

    }
    logger.info("Update employee Failed");
    employeeErrorDto.setCode(RestApiResponseStatus.FAILURE.getCode());
    employeeErrorDto.setStatus(RestApiResponseStatus.FAILURE.getStatus());
    return ResponseEntity.ok(employeeErrorDto);
  }

  @GetMapping("/employeeName/{id}")
  public ResponseEntity<EmployeeNameDto> getEmployeeNameById(@PathVariable Long id,
      EmployeeDto employeeDto) {
    logger.info("Get employeeName byId started");
    EmployeeNameDto employeeNameDto = employeeService.getEmployeeNameById(id);
    return ResponseEntity.ok(employeeNameDto);

  }

  @GetMapping("/employee/{id}")
  public ResponseEntity<EmployeeListByIdDto> getEmployeeDetailsById(@PathVariable Long id) {
    logger.info("Get employeeDetails byId started");

    EmployeeListByIdDto employeeListByIdDto = employeeService.findEmployeeById(id);
    return ResponseEntity.ok(employeeListByIdDto);
  }

  @PostMapping("/employee/import")
  public ResponseEntity<Object> name(@RequestParam("file") MultipartFile file) throws Exception {
    logger.info("Import csv started");

    employeeService.importEmployee(file);
    return ResponseEntity.ok(" successfully import");
  }

  @GetMapping("/employee/export")
  public void exportToExcel(HttpServletResponse response) throws IOException {
    logger.info("Export employee details started");

    response.setContentType("application/octet-stream");
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename= Employee_Details.xlsx";
    response.setHeader(headerKey, headerValue);
    List<Employee> employee = employeeRepository.findAll();
    EmployeeExcelExport employeeController = new EmployeeExcelExport(employee);
    employeeController.export(response);
  }
  
  @DeleteMapping("/employee/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
		logger.info("delete employee started");
		
		if(employeeService.existsEmployeeId(id)) {
			logger.info("delete employee existsDesignationId passed");
			
			employeeService.deleteEmployee(id);
			EmployeeErrorDto employeeErrorDto = new EmployeeErrorDto();
			employeeErrorDto.setCode(RestApiResponseStatus.DELETED.getCode());
			employeeErrorDto.setStatus(RestApiResponseStatus.DELETED.getStatus());
			return ResponseEntity.ok(employeeErrorDto);
			
		}
		
		logger.info("delete designation existsDesignationId failed");
		EmployeeErrorDto employeeErrorDto = new EmployeeErrorDto();
		employeeErrorDto.setCode(RestApiResponseStatus.VALIDATION_FAILURE.getCode());
		employeeErrorDto.setStatus(RestApiResponseStatus.VALIDATION_FAILURE.getStatus());
		return ResponseEntity.ok(employeeErrorDto);
  }
  
  
  
  
  
  
  
  @GetMapping("/employees")
  public List<EmployeeFindAllDto> findAllEmployeeDetails(
		  @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
		  @RequestParam(required = false, defaultValue = "10") Integer pageSize,
		  @RequestParam(required = false, defaultValue = "asc") String direction,
		  @RequestParam(required = false, defaultValue = "employeeId") String property,
		  @RequestParam("employeeId") Long employeeId,
	      @RequestParam("firstName") String firstName, 
	      @RequestParam("lastName") String lastName,
	      @RequestParam("gender") String gender, 
	      @RequestParam("emailId") String emailId,
	      @RequestParam("phoneNo") String phoneNo,
	      @RequestParam("designationName")String designationName) {
	  
    logger.info("Get all employee details started");
    return employeeService.findAllEmployeeDetails(pageNumber, pageSize, direction, property, employeeId, firstName, lastName, gender, emailId, phoneNo, designationName);
  }
  
}
