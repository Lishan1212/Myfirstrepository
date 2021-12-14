package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.PriorityDto;
import com.sgic.defect.server.dto.PriorityIdDto;
import com.sgic.defect.server.dto.PriorityNameDto;

public interface PriorityService {

	public List<PriorityIdDto> findAllPriority();
	public void savePriority(PriorityDto priorityDto);
	
	public void updatePriority(Long Id, PriorityDto PriorityDto);
	
	public List<PriorityNameDto> findAllNamePriority(int pageNumber,int pageSize);
	
	public boolean validateduplicate( PriorityDto priorityDto);
	
	public List<PriorityDto> getAllPriorityByProject(Long id);
	
	public boolean validateprojectid(Long projectId);
	

	public List<PriorityDto> prioritySerachByName(String name,String color ,String priorityLevel);
	public 	void deletePriority(Long id);
	
	boolean existsByPriorityInPriority(Long id);
	boolean isExistById(Long id);

  
	


  
	

}
