package com.sgic.employee.server.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sgic.employee.server.dto.DesignationDto;
import com.sgic.employee.server.dto.EmployeeDto;
import com.sgic.employee.server.dto.EmployeeFindAllDto;
import com.sgic.employee.server.dto.EmployeeListByIdDto;
import com.sgic.employee.server.dto.EmployeeNameDto;
import com.sgic.employee.server.entities.Designation;
import com.sgic.employee.server.entities.Employee;
import com.sgic.employee.server.repositories.DesignationRepository;
import com.sgic.employee.server.repositories.EmployeeRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DesignationRepository designationRepository;

	private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Override
	public String saveEmployee(EmployeeDto employeeDto) {

		logger.info("Save employee started");

		logger.info("Employee save validation started");
		Employee employee = new Employee();
		employee.setFirstName(employeeDto.getFirstName());
		employee.setEmailId(employeeDto.getEmailId());
		employee.setLastName(employeeDto.getLastName());
		employee.setPhoneNo(employeeDto.getPhoneNo());
		employee.setGender(employeeDto.getGender());
		employee.setDesignation(designationRepository.getOne(employeeDto.getDesignationId()));
		employeeRepository.save(employee);

		return ("Saved Successfully");

	}

	@Override
	public String updateEmployee(Long id, EmployeeDto employeeDto) {
		logger.info("Employee update started");
		logger.info("Update employee validation started");

		Employee employee = employeeRepository.findById(id).get();
		employee.setFirstName(employeeDto.getFirstName());
		employee.setLastName(employeeDto.getLastName());
		employee.setGender(employeeDto.getGender());
		employee.setEmailId(employeeDto.getEmailId());
		employee.setPhoneNo(employeeDto.getPhoneNo());
		employeeRepository.save(employee);
		return ("Updated");

	}

	@Override
	public EmployeeNameDto getEmployeeNameById(Long id) {
		logger.info("Get employeeName byId started");

		Employee employee = employeeRepository.findById(id).get();

		EmployeeNameDto employeeNameDto = new EmployeeNameDto();

		employeeNameDto.setFirstName(employee.getFirstName());
		employeeNameDto.setLastName(employee.getLastName());

		return employeeNameDto;

	}

	@Override
	public EmployeeListByIdDto findEmployeeById(Long id) {
		logger.info("Get employeeDetails byId started");
		Employee employee = employeeRepository.findById(id).get();
		EmployeeListByIdDto employeeListByIdDto = new EmployeeListByIdDto();
		employeeListByIdDto.setEmployeeId(id);
		employeeListByIdDto.setFirstName(employee.getFirstName());
		employeeListByIdDto.setLastName(employee.getLastName());
		employeeListByIdDto.setEmailId(employee.getEmailId());
		employeeListByIdDto.setGender(employee.getGender());
		employeeListByIdDto.setPhoneNo(employee.getPhoneNo());
		return employeeListByIdDto;

	}

	public void importEmployee(MultipartFile file) {
		logger.info("Import csv to employee started");
		try {

			List<Employee> employees = new ArrayList<Employee>();

			InputStream input = file.getInputStream();
			CsvParserSettings settings = new CsvParserSettings();
			settings.setHeaderExtractionEnabled(true);
			CsvParser csvParser = new CsvParser(settings);
			List<Record> parseAllRecord = csvParser.parseAllRecords(input);
			parseAllRecord.forEach(record -> {
				Employee employee = new Employee();

				employee.setEmployeeId(record.getLong("Id"));
				employee.setEmailId(record.getString("Email"));
				employee.setDesignation(
						designationRepository.findById(Long.parseLong(record.getString("Designation"))).get());
				employee.setFirstName(record.getString("FirstName"));
				employee.setGender(record.getString("Gender"));
				employee.setLastName(record.getString("LastName"));
				employee.setPhoneNo(record.getString("MobileNumber"));
				employees.add(employee);
			});
			employeeRepository.saveAll(employees);

		} catch (Exception e) {
			logger.info("Import csv to employee Failed");

			System.out.println("error in file uplode");
		}

	}

	@Override
	public boolean checkDublicateFeildsEmailIdAndPhoneNo(EmployeeDto employeeDto) {
		if (!(employeeRepository.existsByEmailId(employeeDto.getEmailId()))
				&& !(employeeRepository.existsByPhoneNo(employeeDto.getPhoneNo()))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkExistsEmployeeId(EmployeeDto employeeDto) {
		if (!(employeeRepository.existsByEmployeeId(employeeDto.getEmployeeId()))) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteEmployee(Long id) {
		logger.info("delete employee impl started");
		employeeRepository.deleteById(id);
	}

	@Override
	public boolean existsEmployeeId(Long id) {
		if(employeeRepository.existsByEmployeeId(id)) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<EmployeeFindAllDto> findAllEmployeeDetails(int pageNumber, int pageSize, String direction, String property, Long employeeId, String firstName, String lastName, String gender, String emailId, String phoneNo, String designationName) {
		logger.info("Get Employee details impl started");
		
		if (property.equals("designationName")) {
			Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Direction.fromString(direction), "designation"));
			
			if (employeeId != null) {
				return employeeRepository.findByEmployeeId(employeeId, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

			} else if (!firstName.isEmpty()) {
				return employeeRepository.findEmployeeByFirstNameIgnoreCase(firstName, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

			} else if (!lastName.isEmpty()) {
				return employeeRepository.findEmployeeByLastNameIgnoreCase(lastName, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

			} else if (!gender.isEmpty()) {
				return employeeRepository.findEmployeeByGenderIgnoreCase(gender, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

			} else if (!emailId.isEmpty()) {
				return employeeRepository.findEmployeeByEmailId(emailId, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

			} else if (!phoneNo.isEmpty()) {
				return employeeRepository.findEmployeeByPhoneNo(phoneNo, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

			} else if (designationRepository.existsByDesignationName(designationName)) {
				Designation designationObject = designationRepository.findByDesignationNameIgnoreCase(designationName);
				return employeeRepository.findEmployeeByDesignation(designationObject, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());
			}

			return employeeRepository.findAll(pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());
			
		}
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Direction.fromString(direction), property));
		
		if (employeeId != null) {
			return employeeRepository.findByEmployeeId(employeeId, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

		} else if (!firstName.isEmpty()) {
			return employeeRepository.findEmployeeByFirstNameIgnoreCase(firstName, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

		} else if (!lastName.isEmpty()) {
			return employeeRepository.findEmployeeByLastNameIgnoreCase(lastName, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

		} else if (!gender.isEmpty()) {
			return employeeRepository.findEmployeeByGenderIgnoreCase(gender, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

		} else if (!emailId.isEmpty()) {
			return employeeRepository.findEmployeeByEmailId(emailId, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

		} else if (!phoneNo.isEmpty()) {
			return employeeRepository.findEmployeeByPhoneNo(phoneNo, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());

		} else if (designationRepository.existsByDesignationName(designationName)) {
			Designation designationObject = designationRepository.findByDesignationNameIgnoreCase(designationName);
			return employeeRepository.findEmployeeByDesignation(designationObject, pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());
		}

		return employeeRepository.findAll(pageable).stream().map(this::convertToEmployeeFindAllDto).collect(Collectors.toList());
			
	}

	public EmployeeFindAllDto convertToEmployeeFindAllDto(Employee employee) {
		logger.info("Convert EmployeeDto started");

		EmployeeFindAllDto employeeFindAllDto = new EmployeeFindAllDto();
		employeeFindAllDto.setEmployeeId(employee.getEmployeeId());
		employeeFindAllDto.setFirstName(employee.getFirstName());
		employeeFindAllDto.setLastName(employee.getLastName());
		employeeFindAllDto.setGender(employee.getGender());
		employeeFindAllDto.setEmailId(employee.getEmailId());
		employeeFindAllDto.setPhoneNo(employee.getPhoneNo());

		Designation designation = employee.getDesignation();
		DesignationDto designationDto = new DesignationDto();
		designationDto.setDesignationId(designation.getDesignationId());
		designationDto.setDesignationName(designation.getDesignationName());
		employeeFindAllDto.setDesignationDto(designationDto);
		return employeeFindAllDto;

	}

}
