package com.sgic.internal.eurekaclient.enums;

public enum RestApiResponseStatus {
	OK(20000,"OK"), CREATED(20100,"CREATED"), VALIDATION_FAILURE(40000,"VALIDATION_FAILURE"),
	FORBIDDEN(40300,"FORBIDDEN"), ERROR(50000, "ERROR"),UPDATED(22000,"UPDATED"),
	 RETRIVED(23000, "SUCCESSFULLY_GET"),FAILURE(44000,"FAILURE"),EXISTS(6005,"ALREADY_EXIST"),DELETED(50005,"DELETE");

	private String status;

	  private Integer code;
	private RestApiResponseStatus(Integer code,String status) {
		this.code = code;
		this.status = status;
	
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
