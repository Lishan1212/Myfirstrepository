package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.DefectTypeDto;

public interface DefectTypeServices {

	public void saveDefectType(DefectTypeDto defectTypeDto);

	public List<DefectTypeDto> findAllDefect(Integer pageNumber, Integer pageSize);

	public void updateDefecttype(Long id, DefectTypeDto defectTypeDto);

	public boolean validateduplicate(DefectTypeDto defectTypeDto);

	public List<DefectTypeDto> getAllDefectTypeByProjectId(Long ProjectId);

	public boolean validateProjectId(Long projectId);

	List<DefectTypeDto> defectTypeSerach(String defectTypeName);

	boolean existsByDefectTypeInDefectType(Long id);

	boolean isexistById(Long id);

	public void deleteDefectType(Long id);
}
