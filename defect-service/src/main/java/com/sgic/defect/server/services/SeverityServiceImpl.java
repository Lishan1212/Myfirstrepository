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
import org.springframework.web.bind.annotation.RequestBody;

import com.sgic.defect.server.dto.SeverityDto;
import com.sgic.defect.server.dto.SeverityIdDto;
import com.sgic.defect.server.dto.SeverityNameDto;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.Severity;
import com.sgic.defect.server.repositories.DefectRepository;
import com.sgic.defect.server.repositories.ProjectRepository;
import com.sgic.defect.server.repositories.SeverityRepository;

@Service
public class SeverityServiceImpl implements SeverityService {
	@Autowired
	public SeverityRepository severityRepository;
	@Autowired
	public DefectRepository defectReposotory;
	@Autowired
	public ProjectRepository projectRepository;
	Logger logger = LoggerFactory.getLogger(SeverityServiceImpl.class);

	@Override
	public void saveSeverity(SeverityDto severityDto) {
		logger.info("Severity save Start");
		
		Severity severity = new Severity();
		severity.setSeverityName(severityDto.getName());
		severity.setSeverityColor(severityDto.getColor());
		severity.setSeverityLevel(severityDto.getSeverityLevel());

		Project project = projectRepository.getOne(severityDto.getProjectId());
		severity.setProject(project);
		severityRepository.save(severity);
	}

	@Override
	public List<SeverityIdDto> findAllSeverity(Integer pageNumber, Integer pageSize, String name) {
		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
		Page<Severity> pageResult = severityRepository.findAll(paging);
		List<Severity> severities = pageResult.getContent();
		return severities.stream().map(this::convertToSeverityIdDto).collect(Collectors.toList());
	}

	private SeverityIdDto convertToSeverityIdDto(Severity severity) {
		logger.info("severity  getall start");
		SeverityIdDto severityIdDto = new SeverityIdDto();
		severityIdDto.setName(severity.getSeverityName());
		severityIdDto.setColor(severity.getSeverityColor());
		severityIdDto.setSeverityLevel(severity.getSeverityLevel());
		return severityIdDto;
	}

	@Override
	public void updateSeverity(Long severityId, @RequestBody SeverityDto severityDto) {
		logger.info("update severity ", severityId);
		Severity severity = severityRepository.findById(severityId).get();
		severity.setSeverityName(severityDto.getName());
		severity.setSeverityColor(severityDto.getColor());
		severity.setSeverityLevel(severityDto.getSeverityLevel());
		severityRepository.save(severity);
	}

	@Override
	public List<SeverityNameDto> findSeverityByName(Severity severity) {
		return ((List<Severity>) severityRepository.findAll()).stream().map(this::convertToSeverityNameDto)
				.collect(Collectors.toList());
	}

	private SeverityNameDto convertToSeverityNameDto(Severity severity) {
		logger.info(" getAllSeverityName");
		SeverityNameDto severityNameDto = new SeverityNameDto();
		severityNameDto.setSeverityId(severity.getSeverityId());
		severityNameDto.setName(severity.getSeverityName());
		return severityNameDto;
	}

	@Override
	public SeverityIdDto findSeverityById(Long severityId)

	{
		logger.info("severity findById", severityId);
		Severity severity = severityRepository.findById(severityId).get();
		SeverityIdDto severityIdDto = new SeverityIdDto();
		severityIdDto.setName(severity.getSeverityName());
		severityIdDto.setColor(severity.getSeverityColor());
		severityIdDto.setSeverityLevel(severity.getSeverityLevel());
		return severityIdDto;
	}



	@Override
	public void deleteSeverity(Long severityId) {
		logger.info("severity delete");
		severityRepository.deleteById(severityId);

	}

	@Override

	public boolean isSeverityNameValidate(String name) {
		if (!severityRepository.existsBySeverityName(name)) {
			return true;
		}
		return false;
	}

	@Override

	public boolean isSeverityColorValidate(String color) {
		if (!severityRepository.existsBySeverityColor(color)) {

			return true;
		}
		return false;
	}

	@Override
	public boolean isFindSeverityByIdValidate(Long severityId) {
		if (severityRepository.existsBySeverityId(severityId)) {
			return true;
		}
		return false;
	}

	@Override
	public List<SeverityDto> getAllSeveritiesByProjectId(Long projectId) {
		Project project = projectRepository.findById(projectId).get();

		return severityRepository.findByProject(project).stream().map(this::convertSeverityDto)
				.collect(Collectors.toList());
	}

	public SeverityDto convertSeverityDto(Severity severity) {
		SeverityDto severityDto = new SeverityDto();
		severityDto.setName(severity.getSeverityName());
		severityDto.setColor(severity.getSeverityColor());
		severityDto.setSeverityLevel(severity.getSeverityLevel());
		if (!(severity.getProject() == null)) {
			severityDto.setProjectId(severity.getProject().getProjectId());
		}
		return severityDto;
	}

	@Override
	
	public boolean validateProjectId(Long projectId) {
		if (projectRepository.existsByProjectId(projectId)) {
			return true;
		}
		return false;
	}

	@Override
	public List<SeverityDto> searchSeverity(String severityLevel, String name, String color) {
		if (!name.isEmpty()) {
			return severityRepository.findBySeverityNameStartsWithIgnoreCase(name).stream()
					.map(this::convertSeverityDto).collect(Collectors.toList());
		}
		if (!severityLevel.isEmpty()) {
			return severityRepository.findBySeverityLevelStartsWithIgnoreCase(severityLevel).stream()
					.map(this::convertSeverityDto).collect(Collectors.toList());
		}

		if (!color.isEmpty()) {
			return severityRepository.findBySeverityColorStartsWithIgnoreCase(color).stream()
					.map(this::convertSeverityDto).collect(Collectors.toList());
		}
		return severityRepository.findAll().stream().map(this::convertSeverityDto).collect(Collectors.toList());
	}

	public SeverityDto convertProjectDto(Severity severity) {
		logger.info("severity search start");
		SeverityDto severityDto = new SeverityDto();
		severityDto.setName(severity.getSeverityName());
		severityDto.setColor(severity.getSeverityColor());
		severityDto.setSeverityLevel(severity.getSeverityLevel());

		return severityDto;
	}

	@Override
	public boolean validateSeverityIdinDefect(Long severityId) {
		if (defectReposotory.existsBySeverity(severityRepository.findById(severityId).get())) {
			return true;
		}
		return false;
	}

}
