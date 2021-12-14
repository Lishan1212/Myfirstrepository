package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.ProjectAllocationDto;
import com.sgic.defect.server.dto.ProjectAllocationIdDto;

public interface ProjectAllocationService {
	void saveProjectAllocation(ProjectAllocationDto projectAllocationDto);

	void updateProjectAllocation(Long projectAllocationId, ProjectAllocationDto projectAllocationDto);

	List<ProjectAllocationIdDto> findAllProjectAllocation(Integer pageNumber, Integer pageSize, String sortBy);

	Object findProjectAllocationById(Long projectAllocationId);

	List<ProjectAllocationDto> getAllProjectAllocationByProjectId(Long projectId);

	public List<ProjectAllocationDto> projectAllocationSearch(Long employeeId, String fullName, String roleName,
			Long percentage, String projectName);

	public void deleteProjectAllocation(Long projectAllocationId);

	public Long getAllAllocationPercentageByEmployeeId(Long employeeId);

	public boolean isProjectAllocationfullNameAndProjectIdValidate(String fullName, Long projectId);

	public boolean isProjectAllocationValidbyProjectId(Long projectId);

	public boolean isProjectAllocationValidbyEmployeeId(Long employeeId);

	public boolean isProjectAllocationPercentageValidation(Long employeeId, Long percentage);

	public boolean isFindProjectAllocationByIdValidate(Long projectAllocationId);

}
