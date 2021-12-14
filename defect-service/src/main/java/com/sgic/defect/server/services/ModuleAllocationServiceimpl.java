package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.ModuleAllocationDto;
import com.sgic.defect.server.dto.ModuleAllocationGetDto;
import com.sgic.defect.server.dto.ModuleAllocationSaveDto;
import com.sgic.defect.server.dto.ModuleAllocationSubModuleDto;
import com.sgic.defect.server.entities.ModuleAllocation;
import com.sgic.defect.server.repositories.ModuleAllocationRepository;
import com.sgic.defect.server.repositories.ModuleRepository;
import com.sgic.defect.server.repositories.ProjectRepository;
import com.sgic.defect.server.repositories.SubmoduleRepository;

import ch.qos.logback.classic.Logger;

@Service
public class ModuleAllocationServiceimpl implements ModuleAllocationServices {
	Logger logger = (Logger) LoggerFactory.getLogger(ModuleAllocationServiceimpl.class);

	@Autowired
	ModuleAllocationRepository moduleAllocationRepository;

	@Autowired
	SubmoduleRepository subModuleRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	ProjectRepository projectRepository;

	// Save Module Allocation
	@Override
	public void savemoduleAllocation(ModuleAllocationSaveDto moduleAllocationDto) {
		logger.info("Module allocation entity save start");
		ModuleAllocation moduleAllocation = new ModuleAllocation();
		moduleAllocation.setFullName(moduleAllocationDto.getFullName());
		moduleAllocation.setAllocationStatus(true);
		moduleAllocation.setSubmodule(subModuleRepository.getOne(moduleAllocationDto.getSubModuleId()));
		moduleAllocationRepository.save(moduleAllocation);

	}

	@Override
	public void updateModuleAllocation(Long Id, ModuleAllocationGetDto moduleAllocationDto2) {
		logger.info("Module allocation update start");
		ModuleAllocation moduleAllocation = moduleAllocationRepository.findById(Id).get();
		moduleAllocation.setFullName(moduleAllocationDto2.getFullName());
		moduleAllocationRepository.save(moduleAllocation);
	}

	@Override
	public List<ModuleAllocationGetDto> getAllModuleAllocation(Integer pageNumber, Integer pageSize) {
		Pageable paging= PageRequest.of(pageNumber, pageSize);
		Page<ModuleAllocation> pageResult=moduleAllocationRepository.findAll(paging);
		List<ModuleAllocation> moduleallocations=pageResult.getContent();
		logger.info("Module allocation entity Get All Start");

		return (moduleallocations.stream()
				.map(this::convertToModuleAllocationDto2).collect(Collectors.toList()));
	}

	private ModuleAllocationGetDto convertToModuleAllocationDto2(ModuleAllocation moduleAllocation) {
		ModuleAllocationGetDto moduleAllocationDto2 = new ModuleAllocationGetDto();

		moduleAllocationDto2.setFullName(moduleAllocation.getFullName());
		return moduleAllocationDto2;
	}

	@Override

	public ModuleAllocationGetDto findById(Long Id) {
		logger.info("Module allocation entity find By Id");
		ModuleAllocation moduleAllocation = moduleAllocationRepository.findById(Id).get();
		ModuleAllocationGetDto moduleAllocationDto2 = new ModuleAllocationGetDto();

		moduleAllocationDto2.setFullName(moduleAllocation.getFullName());
		return moduleAllocationDto2;
	}

	@Override
	public boolean fullNameValidation(String fullName) {
		if (moduleAllocationRepository.existsByFullName(fullName)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean moduleAllocationIdValidation(Long moduleAllocationId) {

		if (moduleAllocationRepository.existsBymoduleAllocationId(moduleAllocationId)) {
			return true;

		}
		return false;

	}

	@Override
	public boolean isExistSubmodule(Long subModuleId) {

		if (subModuleRepository.existsBySubModuleId(subModuleId)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean moduleAllocationValidation(Long subModuleId) {
		List<ModuleAllocationSubModuleDto> moduleAllocationList = moduleAllocationRepository
				.findByAllocationStatusAndSubmodule(true, subModuleRepository.findById(subModuleId).get()).stream()
				.map(this::convertIntoAllocationStatus).collect(Collectors.toList());

		if (moduleAllocationList.isEmpty()) {
			return true;
		}
		return false;
	}

	private ModuleAllocationSubModuleDto convertIntoAllocationStatus(ModuleAllocation moduleAllocation) {
		ModuleAllocationSubModuleDto moduleAllocationSubModuleDto = new ModuleAllocationSubModuleDto();
		moduleAllocationSubModuleDto.setAllocationStatus(moduleAllocation.getAllocationStatus());
		moduleAllocationSubModuleDto.setSubModuleId(moduleAllocation.getSubmodule().getSubModuleId());
		return moduleAllocationSubModuleDto;
	}

	@Override
	public void moduleDeAllocation(Long moduleAllocationId) {
		ModuleAllocation moduleAllocation = moduleAllocationRepository.findById(moduleAllocationId).get();
		if (moduleAllocation.getAllocationStatus() == true) {
			moduleAllocation.setAllocationStatus(false);
			moduleAllocationRepository.save(moduleAllocation);
		}

	}
	@Override
	public List<ModuleAllocationGetDto>moduleAllocationsearch(String fullName)
	{
		if(!fullName.isEmpty())
		{
			logger.info("Full name is not blank");
			return moduleAllocationRepository.findByFullName(fullName).stream().map(this::convertModuleAllocation).collect(Collectors.toList()); 
		}
		logger.info("Fullname is blank");
		return moduleAllocationRepository.findAll().stream().map(this::convertModuleAllocation).collect(Collectors.toList());
	}
	
	private ModuleAllocationGetDto convertModuleAllocation(ModuleAllocation moduleAllocation )
	{
		ModuleAllocationGetDto  moduleAllocationGetDto =new ModuleAllocationGetDto();
		moduleAllocationGetDto.setFullName(moduleAllocation.getFullName());
		moduleAllocationGetDto.setAllocationStatus(false);
		return moduleAllocationGetDto;
		
	}

}
