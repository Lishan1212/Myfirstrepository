package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.ModuleDto;
import com.sgic.defect.server.dto.ModuleFindDto;
import com.sgic.defect.server.dto.ModuleNameDto;
import com.sgic.defect.server.entities.Modules;
import com.sgic.defect.server.entities.Submodule;
import com.sgic.defect.server.repositories.ModuleRepository;
import com.sgic.defect.server.repositories.ProjectRepository;
import com.sgic.defect.server.repositories.SubmoduleRepository;
import com.sgic.defect.server.util.Utills;
import com.sgic.defect.server.util.ValidationMessages;

@Service
public class ModuleServiceImpl implements ModuleService {
	private final Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	SubmoduleRepository submoduleRepository;

	@Override
	public String saveModule(ModuleDto moduleDto) {
		logger.info("Module save is started in Module Service");

		Modules modules = new Modules();
		modules.setModuleName(moduleDto.getModuleName());
		modules.setProject(projectRepository.getOne(moduleDto.getProjectId()));
		moduleRepository.save(modules);
		logger.info("Module saved successfully");
		return ValidationMessages.SAVE_SUCCESS;

	}

	@Override
	public String updateModule(Long id, ModuleDto moduleDto) {
		logger.info("Module updated started");

		if (validateModuleId(id)) {
			logger.info("module Id exist");
			if (Utills.stringValidationUsingPattern(moduleDto.getModuleName())) {
				Modules modules = moduleRepository.findById(id).get();
				modules.setModuleName(moduleDto.getModuleName());
				moduleRepository.save(modules);

				return ValidationMessages.UPDATE_SUCCESS;
			}

			return ValidationMessages.UPDATE_FAILED;
		}

		return "Module Id not exists";

	}

	@Override
	public List<ModuleNameDto> findAllModule() {
		logger.info("module find all started");
		return moduleRepository.findAll().stream().map(this::convertIntoDto).collect(Collectors.toList());
	}

	public ModuleNameDto convertIntoDto(Modules modules) {
		ModuleNameDto moduleNameDto = new ModuleNameDto();
		moduleNameDto.setModuleName(modules.getModuleName());
		moduleNameDto.setModuleId(modules.getModuleId());
		return moduleNameDto;
	}

	@Override
	public boolean validateProjectId(ModuleDto moduleDto) {
		if (projectRepository.existsByProjectId(moduleDto.getProjectId())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateModuleId(Long moduleId) {
		if (moduleRepository.existsByModuleId(moduleId)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateDuplicateModule(ModuleDto moduleDto) {
		List<ModuleNameDto> moduleNameList = moduleRepository
				.findByModuleNameAndProject(moduleDto.getModuleName(),
						projectRepository.findById(moduleDto.getProjectId()).get())
				.stream().map(this::convertIntoDto).collect(Collectors.toList());
		if (moduleNameList.isEmpty()) {

			return true;
		}
		return false;
	}

	@Override
	public ModuleFindDto findModuleById(Long moduleId) {
		Modules modules = moduleRepository.findById(moduleId).get();
		ModuleFindDto moduleFindDto = new ModuleFindDto();
		moduleFindDto.setModuleId(modules.getModuleId());
		moduleFindDto.setModuleName(modules.getModuleName());
		moduleFindDto.setProjectId(modules.getProject().getProjectId());
		return moduleFindDto;
	}

	@Override
	public List<ModuleNameDto> findAllPagination(Integer pageNumber) {

		logger.info("Pagination start in module servie implementation");

		Pageable pageable = PageRequest.of(pageNumber - 1, 2, Sort.by("moduleName"));

		Page<Modules> page = moduleRepository.findAll(pageable);
		List<Modules> moduleList = page.getContent();
		List<ModuleNameDto> finalList = moduleList.stream().map(this::convertIntoDto).collect(Collectors.toList());
		logger.info("Pagination end in module servie implementation");
		return finalList;
	}
	
	@Override
	public List<ModuleFindDto>moduleSearch(String moduleName )
	{
		
		if(!moduleName.isEmpty()) {
			logger.info("Module Name is not blank");
		return moduleRepository.findByModuleName(moduleName).stream().map(this::convert).collect(Collectors.toList());
		}
		
		logger.info("Module Name is blank");
		return moduleRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
	}
	
	
	public ModuleFindDto convert(Modules modules) {
		ModuleFindDto moduleFindDto = new ModuleFindDto();
		moduleFindDto.setModuleName(modules.getModuleName());
		moduleFindDto.setModuleId(modules.getModuleId());
		return moduleFindDto;
	}

	
	
	
	

	@Override
	public List<ModuleNameDto> moduleSorting(String direction, String properties) {
		logger.info("module sorting started");

		return moduleRepository.findAll(Sort.by(Sort.Direction.fromString(direction), properties)).stream()
				.map(this::convertIntoDto).collect(Collectors.toList());
	}

	@Override
	public void deleteModule(Long moduleId) {
		logger.info("Module delete method invoked");

		moduleRepository.deleteById(moduleId);
	}

	@Override
	public boolean validateModuleInSubmodule(Long moduleId) {
		logger.info("Module ID validation in Submodule invoked");
		if(submoduleRepository.existsByModule(moduleRepository.getOne(moduleId))) {
			logger.info("Module ID exists in submodule repository");
			return true;
		}
		
		return false;
	}

}
