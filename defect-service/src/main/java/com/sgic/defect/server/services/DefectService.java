package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.DefectDto;
import com.sgic.defect.server.dto.DefectFindAllDto;
import com.sgic.defect.server.dto.DefectUpdateDto;

public interface DefectService {
	public void saveDefect(DefectDto defectDto);

	public String updateDefect(Long Id, DefectUpdateDto defectUpdateDto);

	public List<DefectFindAllDto> findAlldefect(Long projectId, Integer pageNumber, String direction,
			String property, String severityName, String priorityName, String defectStatusName, String defectTypeName,
			String subModuleName, String releaseName, Long assignedUserId, Long creativeUserId);

	public DefectFindAllDto findByIdDefect(Long id);

	public boolean existsByDefectId(Long id);

	public boolean validateField(DefectDto defectDto);

	public boolean validateProjectId(Long projectId);

	public void deleteDefect(Long Id);

	public boolean exsitsdelete(Long Id);

}
