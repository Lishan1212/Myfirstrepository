package com.sgic.employee.server.enums;

public enum RestApiResponseStatus {


  OK(20000,"OK" ), 
  CREATED(21000,"CREATED"), 
  VALIDATION_FAILURE(40000,"VALIDATION_FAILURE"), 
  UPDATED(22000,"UPDATED"), 
  RETRIEVED(23000,"SUCCESSFULLY_GET"),
  DELETED(24000, "DELETED"),
  FAILURE(44000,"FAILURE");
  		

  private String status;

  private Integer code;

  RestApiResponseStatus(Integer code,String status) {
    this.status = status;
    this.code = code;
  }

  public String getStatus() {
    return status;
  }

  public Integer getCode() {
    return code;
  }

  @Override
  public String toString() {
    return status + ":" + code;
  }

}
