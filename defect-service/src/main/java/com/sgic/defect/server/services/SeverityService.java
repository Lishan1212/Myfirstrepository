package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.SeverityDto;
import com.sgic.defect.server.dto.SeverityIdDto;
import com.sgic.defect.server.dto.SeverityNameDto;
import com.sgic.defect.server.entities.Severity;

public interface SeverityService {

	public void saveSeverity(SeverityDto severityDto);

	public List<SeverityIdDto> findAllSeverity(Integer pageNumber, Integer pageSize, String name);

	public void updateSeverity(Long severityId, SeverityDto severityDto);

	public List<SeverityNameDto> findSeverityByName(Severity severity);

	public Object findSeverityById(Long severityId);

	public List<SeverityDto> getAllSeveritiesByProjectId(Long projectId);

	public void deleteSeverity(Long severityId);

	public boolean isSeverityNameValidate(String name);

	public boolean isSeverityColorValidate(String color);

	public boolean isFindSeverityByIdValidate(Long severityId);

	public boolean validateProjectId(Long projectId);

	public List searchSeverity(String severityLevel, String name, String color);

	public boolean validateSeverityIdinDefect(Long severityId);

}
