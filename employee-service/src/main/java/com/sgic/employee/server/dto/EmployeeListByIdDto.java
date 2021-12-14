package com.sgic.employee.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeListByIdDto {
  private Long employeeId;
  
  private String firstName;
  
  private String lastName;
  
  private String gender;
  
  private String emailId;
  
  private String phoneNo;

}
