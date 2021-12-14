package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.controller.DefectStatusController;
import com.sgic.defect.server.dto.DefectStatusDto;
import com.sgic.defect.server.dto.DefectStatusFindAllDto;
import com.sgic.defect.server.entities.DefectStatus;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.repositories.DefectRepository;
import com.sgic.defect.server.repositories.DefectStatusRepository;
import com.sgic.defect.server.repositories.ProjectRepository;

@Service
public class DefectStatusServiceImpl implements DefectStatusService {
	private final Logger logger = LoggerFactory.getLogger(DefectStatusController.class);

	@Autowired
	DefectStatusRepository defectStatusRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	DefectRepository defectRepository;

	@Override
	public void saveDefectStatus(DefectStatusDto defectStatusDto) {
		DefectStatus defectStatus = new DefectStatus();
		defectStatus.setDefectStatusName(defectStatusDto.getDefectStatusName());
		defectStatus.setDefectStatusColor(defectStatusDto.getDefectStatusColor());

		Project project = projectRepository.getOne(defectStatusDto.getProjectId());
		defectStatus.setProject(project);

		logger.info("second if satatement DefectStatus Started");
		defectStatusRepository.save(defectStatus);

	}

	@Override
	public List<DefectStatusFindAllDto> defectStatusFindAllDto(int pageNumber, int pageSize) {
		logger.info(" Find all defectStatus started");
		Pageable paging = PageRequest.of(pageNumber - 1, pageSize);
		return defectStatusRepository.findAll(paging).stream().map(this::convertIntoDto).collect(Collectors.toList());
	}

	private DefectStatusFindAllDto convertIntoDto(DefectStatus defectStatus) {
		DefectStatusFindAllDto defectStatusFIndAllDto = new DefectStatusFindAllDto();
		defectStatusFIndAllDto.setStatusName(defectStatus.getDefectStatusName());
		defectStatusFIndAllDto.setStatusColor(defectStatus.getDefectStatusColor());
		logger.info(" All defectStatus found");
		return defectStatusFIndAllDto;
	}

	@Override
	public void updateDefectStatus(Long id, DefectStatusDto defectStatusDto) {

		DefectStatus defectStatus = defectStatusRepository.findById(id).get();
		defectStatus.setDefectStatusName(defectStatusDto.getDefectStatusName());
		defectStatus.setDefectStatusColor(defectStatusDto.getDefectStatusColor());
		defectStatusRepository.save(defectStatus);
	}

	@Override
	public List<DefectStatusDto> findDefectStatusByProjectId(Long id) {

		Project project = new Project();
		BeanUtils.copyProperties(projectRepository.findById(id).get(), project);
		return defectStatusRepository.findAllByProject(project).stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}

	private DefectStatusDto convertToDto(DefectStatus defectStatus) {
		DefectStatusDto defectStatusDto = new DefectStatusDto();
		defectStatusDto.setDefectStatusName(defectStatus.getDefectStatusName());
		defectStatusDto.setDefectStatusColor(defectStatus.getDefectStatusColor());
		defectStatusDto.setProjectId(defectStatus.getProject().getProjectId());
		logger.info(" DefectStatus find");
		return defectStatusDto;
	}

	@Override
	public boolean validateField(DefectStatusDto defectStatusDto) {
		if (projectRepository.existsByProjectId(defectStatusDto.getProjectId())) {
			return true;

		}
		return false;
	}

	@Override
	public boolean updateDefectStatus(Long id, DefectStatusFindAllDto defectStatusFindAllDto) {
		if (defectStatusRepository.existsByDefectStatusName(defectStatusFindAllDto.getStatusName())) {

			return true;
		}
		return false;
	}

	@Override
	public List<DefectStatusFindAllDto> findDefectStatusByName(String defectStatusName, String defectStatusColor) {
		if (!defectStatusName.isEmpty()) {
			logger.info("defectStatusName is not blank");
			return defectStatusRepository.findByDefectStatusName(defectStatusName).stream()
					.map(this::ConvertDefectStatus).collect(Collectors.toList());
		}

		if (!defectStatusColor.isEmpty()) {
			logger.info("defectStatusColor is not blank");
			return defectStatusRepository.findByDefectStatusColor(defectStatusColor).stream()
					.map(this::ConvertDefectStatus).collect(Collectors.toList());
		}

		logger.info("defectStatusName and defectStatusColor  are blank");
		return defectStatusRepository.findAll().stream().map(this::ConvertDefectStatus).collect(Collectors.toList());

	}

	private DefectStatusFindAllDto ConvertDefectStatus(DefectStatus defectStatus) {
		DefectStatusFindAllDto defectStatusFIndAllDto = new DefectStatusFindAllDto();
		defectStatusFIndAllDto.setStatusName(defectStatus.getDefectStatusName());
		defectStatusFIndAllDto.setStatusColor(defectStatus.getDefectStatusColor());
		return defectStatusFIndAllDto;
	}

	@Override
	public boolean isExistDefectStatusIdInDefect(Long Id) {
		System.out.println(defectRepository.existsByDefectStatus(defectStatusRepository.findById(Id).get()));
		if (defectStatusRepository.existsById(Id)
				&& !defectRepository.existsByDefectStatus(defectStatusRepository.findById(Id).get())) {
			return true;
		}

		return false;

	}

	@Override
	public boolean isExistsByDefectStatusId(Long defectStatusId) {
		if (defectStatusRepository.existsById(defectStatusId)) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteDefectStatus(Long defectStatusId) {
		defectStatusRepository.deleteById(defectStatusId);

	}

	@Override
	public boolean isDefectStatusNameValidate(String name) {
		if (!defectStatusRepository.existsByDefectStatusName(name)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isDefectStatusColorValidate(String color) {
		if (!defectStatusRepository.existsByDefectStatusColor(color)) {
			return true;
		}
		return false;
	}

	

}
