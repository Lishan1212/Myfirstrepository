package com.sgic.employee.server.services;

import java.util.List;
import com.sgic.employee.server.dto.DesignationDto;
import com.sgic.employee.server.entities.Designation;

public interface DesignationService {

  public String saveDesignation(DesignationDto designationDto);

  public void deleteDesignation(Long id);

  public String updateDesignation(Long id, DesignationDto designationDto);

  public boolean validateError(DesignationDto designationDto);

  public boolean validateErrorUpdate(DesignationDto designationDto);

  public boolean validateDesignationInEmployee(Long id);
  
  public boolean existsDesignationId(Long id);
  
  public List<DesignationDto> getAllDesignation(int pageNumber, int pageSize, String direction, String property, String designationName);
  
}
