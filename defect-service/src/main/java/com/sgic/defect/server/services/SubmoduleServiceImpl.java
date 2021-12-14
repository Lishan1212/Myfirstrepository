package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.ModuleAllocationSubModuleDto;
import com.sgic.defect.server.dto.SubmoduleDto;
import com.sgic.defect.server.dto.SubmoduleNameDto;
import com.sgic.defect.server.entities.ModuleAllocation;
import com.sgic.defect.server.entities.Submodule;
import com.sgic.defect.server.repositories.ModuleAllocationRepository;
import com.sgic.defect.server.repositories.ModuleRepository;
import com.sgic.defect.server.repositories.SubmoduleRepository;
import com.sgic.defect.server.util.Utills;
import com.sgic.defect.server.util.ValidationMessages;

@Service
public class SubmoduleServiceImpl implements SubmoduleService {
	@Autowired
	SubmoduleRepository submoduleRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	ModuleAllocationRepository moduleAllocationRepository;

	@Autowired
	ModuleServiceImpl moduleServiceImpl;

	private final Logger logger = LoggerFactory.getLogger(SubmoduleServiceImpl.class);

	@Override
	public String saveSubmodule(SubmoduleDto submoduleDto) {
		logger.info("Save submodule started");
		Submodule submodule = new Submodule();
		submodule.setSubModuleName(submoduleDto.getSubmoduleName());
		submodule.setModule(moduleRepository.getOne(submoduleDto.getModuleId()));
		submoduleRepository.save(submodule);
		logger.info("submodule saved successfully");
		return ValidationMessages.SAVE_SUCCESS;

	}

	@Override
	public String updateSubmdule(Long id, SubmoduleNameDto submoduleNameDto) {
		logger.info("Submodule update started");

		if (validateSubmoduleId(id)) {
			logger.info("Submodule update validation started");

			if (Utills.stringValidationUsingPattern(submoduleNameDto.getSubmoduleName())) {
				Submodule submodule = submoduleRepository.findById(id).get();
				submodule.setSubModuleName(submoduleNameDto.getSubmoduleName());
				submoduleRepository.save(submodule);
				return ValidationMessages.UPDATE_SUCCESS;
			}
			return ValidationMessages.UPDATE_FAILED;
		}
		logger.info("Submodule update validation Failed");

		return "Id not found";

	}

	private SubmoduleNameDto convertIntoDto(Submodule submodule) {
		logger.info("Convert to SubmoduleNameDto started");

		SubmoduleNameDto submoduleNameDto = new SubmoduleNameDto();
		submoduleNameDto.setSubmoduleName(submodule.getSubModuleName());
		submoduleNameDto.setSubmoduleId(submodule.getSubModuleId());
		return submoduleNameDto;
	}

	@Override
	public List<SubmoduleNameDto> findAllSubmoduleByModuleId(Long moduleId) {
		logger.info("Get SubmoduleNameDto details started");

		return submoduleRepository.findByModule(moduleRepository.getOne(moduleId)).stream().map(this::convertIntoDto)
				.collect(Collectors.toList());
	}

	@Override
	public boolean validateSubmoduleId(Long id) {
		logger.info("Submodule id validation method invoked");
		if (submoduleRepository.existsBySubModuleId(id)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateeModuleDuplicateValue(SubmoduleDto submoduleDto) {
		logger.info("module duplicate value validation method invoked");
		List<SubmoduleNameDto> submoduleList = submoduleRepository
				.findBySubModuleNameAndModule(submoduleDto.getSubmoduleName(),
						moduleRepository.findById(submoduleDto.getModuleId()).get())
				.stream().map(this::convertIntoDto).collect(Collectors.toList());
		if (submoduleList.isEmpty()) {
			return true;
		}

		return false;
	}

	@Override
	public SubmoduleNameDto findSubmoduleById(Long subModuleId) {
		logger.info("find submodule by id method invoked");
		Submodule submodule = submoduleRepository.findById(subModuleId).get();
		logger.info("submodule object finded by its ID");
		SubmoduleNameDto submoduleNameDto = new SubmoduleNameDto();
		submoduleNameDto.setSubmoduleName(submodule.getSubModuleName());
		submoduleNameDto.setSubmoduleId(submodule.getSubModuleId());
		
		return submoduleNameDto;
	}

	@Override
	public boolean validateModuleId(Long moduleId) {
		logger.info("Module ID validation method invoked");

		if (moduleRepository.existsByModuleId(moduleId)) {
			logger.info("Module ID is exist in module repository");
			return true;
		}
		return false;
	}

	@Override
	public void deleteBySubmoduleId(Long subModuleId) {
		logger.info("submodule delete method invoked");

		submoduleRepository.deleteById(subModuleId);

	}

	private ModuleAllocationSubModuleDto convertForAllocation(ModuleAllocation moduleAllocation) {

		logger.info("list convertion started");

		ModuleAllocationSubModuleDto moduleAllocationSubModuleDto = new ModuleAllocationSubModuleDto();
		moduleAllocationSubModuleDto.setAllocationStatus(moduleAllocation.getAllocationStatus());
		moduleAllocationSubModuleDto.setSubModuleId(moduleAllocationSubModuleDto.getSubModuleId());
		return moduleAllocationSubModuleDto;
	}

	@Override
	public boolean existSubmoduleInModuleAllocation(Long subModuleId) {
		logger.info("submodule exist validation function invoked");

		List<ModuleAllocationSubModuleDto> submoduleList = moduleAllocationRepository
				.findByAllocationStatusAndSubmodule(true, submoduleRepository.findById(subModuleId).get()).stream()
				.map(this::convertForAllocation).collect(Collectors.toList());

		if (submoduleList.isEmpty()) {
			logger.info("submodule list is empty");
			if (moduleAllocationRepository.existsBySubmodule(submoduleRepository.getOne(subModuleId))) {
				logger.info("submodule  is not available in module allocation");
				moduleAllocationRepository.deleteById(moduleAllocationRepository
						.findBySubmodule(submoduleRepository.getOne(subModuleId)).getModuleAllocationId());
				return true;
			}

			return true;

		}

		return false;
	}

}
