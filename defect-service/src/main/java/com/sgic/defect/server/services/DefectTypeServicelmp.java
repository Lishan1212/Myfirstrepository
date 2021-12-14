package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.controller.DefectTypeController;
import com.sgic.defect.server.dto.DefectTypeDto;
import com.sgic.defect.server.entities.DefectType;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.repositories.DefectRepository;
import com.sgic.defect.server.repositories.DefectTypeRepository;
import com.sgic.defect.server.repositories.ProjectRepository;

@Service
public class DefectTypeServicelmp implements DefectTypeServices {
	@Autowired
	DefectTypeRepository defectTypeRepository;
	@Autowired
	DefectRepository defectRepository;

	@Autowired
	ProjectRepository projectRepository;
	private final Logger Logger = LoggerFactory.getLogger(DefectTypeController.class);

	@Override
	public void saveDefectType(DefectTypeDto defectTypeDto) {
		Logger.info(" defectType Started");
		DefectType defectType = new DefectType();
		defectType.setDefectTypeName(defectTypeDto.getDefectTypeName());
		Project project = projectRepository.getOne(defectTypeDto.getProjectId());
		defectType.setProject(project);
		defectTypeRepository.save(defectType);
	}

	@Override
	public List<DefectTypeDto> findAllDefect(Integer pageNumber, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<DefectType> pageResult = defectTypeRepository.findAll(paging);
		List<DefectType> defecttypes = pageResult.getContent();
		Logger.info(" alldefectType Started");
		return defecttypes.stream().map(this::convertsDefectsTypeDto).collect(Collectors.toList());
	}

	private DefectTypeDto convertsDefectsTypeDto(DefectType defectType) {

		DefectTypeDto defectTypeDto = new DefectTypeDto();
		defectTypeDto.setDefectTypeName(defectType.getDefectTypeName());
		return defectTypeDto;
	}

	@Override
	public void updateDefecttype(Long id, DefectTypeDto defectTypeDto) {
		Logger.info(" defectType  findById ");
		DefectType defectType = defectTypeRepository.findById(id).get();
		defectType.setDefectTypeName(defectTypeDto.getDefectTypeName());
		defectTypeRepository.save(defectType);

	}

	public DefectType convert(DefectType def) {
		Logger.info("get defectType Name ");
		DefectType defecttype = new DefectType();
		defecttype.setDefectTypeName(def.getDefectTypeName());
		return defecttype;
	}

	@Override
	public boolean validateduplicate(DefectTypeDto defectTypeDto) {
		List<DefectType> defecttypelist = defectTypeRepository
				.findByDefectTypeNameAndProject(defectTypeDto.getDefectTypeName(),
						projectRepository.findById(defectTypeDto.getProjectId()).get())
				.stream().map(this::convert).collect(Collectors.toList());
		if (defecttypelist.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<DefectTypeDto> getAllDefectTypeByProjectId(Long projectId) {
		Logger.info("get all defect type by project Id");
		Project project = new Project();
		BeanUtils.copyProperties(projectRepository.findById(projectId).get(), project);
		return defectTypeRepository.findByProject(project).stream().map(this::convertDefectTypeDto)
				.collect(Collectors.toList());
	}

	public DefectTypeDto convertDefectTypeDto(DefectType defectType) {

		DefectTypeDto defecttypedto = new DefectTypeDto();
		defecttypedto.setDefectTypeName(defectType.getDefectTypeName());
		if (!(defectType.getProject() == null)) {
			defecttypedto.setProjectId(defectType.getProject().getProjectId());

		}
		return defecttypedto;
	}

	@Override
	public boolean validateProjectId(Long id) {
		Project project = projectRepository.findById(id).get();
		if (projectRepository.existsByProjectId(id)) {
			return true;
		}
		return false;
	}

	@Override
	public List<DefectTypeDto> defectTypeSerach(String defectTypeName) {
		Logger.info("defecttypename not blank");
		if (!defectTypeName.isEmpty()) {

			return defectTypeRepository.findByDefectTypeName(defectTypeName).stream().map(this::convertsDefects)
					.collect(Collectors.toList());
		}
		Logger.info("defecttypename are blank");
		return defectTypeRepository.findAll().stream().map(this::convertsDefects).collect(Collectors.toList());

	}

	private DefectTypeDto convertsDefects(DefectType defectType) {

		DefectTypeDto defectTypeDto = new DefectTypeDto();
		defectTypeDto.setDefectTypeName(defectType.getDefectTypeName());
		return defectTypeDto;
	}

	
	


	@Override
	public boolean existsByDefectTypeInDefectType(Long id) {
		DefectType defectType=defectTypeRepository.findById(id).get();
		if(defectRepository.existsByDefectType(defectType)) {
		return true;
	}
		return false;
	}

	@Override
	public boolean isexistById(Long id) {
		if(defectTypeRepository.existsByDefectTypeId(id)) {
		return true;
		
	}
	return false;
}

	@Override
	public void deleteDefectType(Long id) {
		Logger.info("delete defect type start");
		defectTypeRepository.deleteById(id);
		
	}
}


