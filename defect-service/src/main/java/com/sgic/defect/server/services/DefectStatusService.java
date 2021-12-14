package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.DefectStatusDto;
import com.sgic.defect.server.dto.DefectStatusFindAllDto;

public interface DefectStatusService {

	public void saveDefectStatus(DefectStatusDto defectStatusDto);

	public List<DefectStatusFindAllDto> defectStatusFindAllDto(int pageNumber,int pageSize );

	public boolean updateDefectStatus(Long id, DefectStatusFindAllDto defectStatusFindAllDto);

	public boolean validateField(DefectStatusDto defectStatusDto);

	void updateDefectStatus(Long id, DefectStatusDto defectStatusDto);

	List<DefectStatusDto> findDefectStatusByProjectId(Long id);
	
	List<DefectStatusFindAllDto> findDefectStatusByName(String defectStatusName,String defectStatusColor);
	
	public boolean isExistDefectStatusIdInDefect(Long Id);
	
	public boolean isExistsByDefectStatusId(Long defectStatusId);
	
	public void deleteDefectStatus(Long defectStatusId);
	
	public boolean isDefectStatusNameValidate(String name);

	public boolean isDefectStatusColorValidate(String color);
}

