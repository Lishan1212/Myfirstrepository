package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.DefectDto;
import com.sgic.defect.server.dto.DefectFindAllDto;
import com.sgic.defect.server.dto.DefectSearchDto;
import com.sgic.defect.server.dto.DefectStatusFindAllDto;
import com.sgic.defect.server.dto.DefectTypeFindDto;
import com.sgic.defect.server.dto.DefectUpdateDto;
import com.sgic.defect.server.dto.PriorityFindDto;
import com.sgic.defect.server.dto.ReleasesFindAllDto;
import com.sgic.defect.server.dto.SeverityFindDto;
import com.sgic.defect.server.dto.SubmoduleNameDto;
import com.sgic.defect.server.entities.Defect;
import com.sgic.defect.server.entities.DefectStatus;
import com.sgic.defect.server.entities.DefectType;
import com.sgic.defect.server.entities.Priority;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.Releases;
import com.sgic.defect.server.entities.Severity;
import com.sgic.defect.server.entities.Submodule;
import com.sgic.defect.server.repositories.DefectRepository;
import com.sgic.defect.server.repositories.DefectStatusRepository;
import com.sgic.defect.server.repositories.DefectTypeRepository;
import com.sgic.defect.server.repositories.PriorityRepository;
import com.sgic.defect.server.repositories.ProjectRepository;
import com.sgic.defect.server.repositories.ProjectTypeRepository;
import com.sgic.defect.server.repositories.ReleasesRepository;
import com.sgic.defect.server.repositories.SeverityRepository;
import com.sgic.defect.server.repositories.SubmoduleRepository;

@Service
public class DefectServiceImpl implements DefectService {

	private final Logger logger = LoggerFactory.getLogger(DefectServiceImpl.class);
	@Autowired
	DefectRepository defectRepository;

	@Autowired
	SeverityRepository severityRepository;

	@Autowired
	PriorityRepository priorityRepository;

	@Autowired
	DefectStatusRepository defectStatusRepository;

	@Autowired
	DefectTypeRepository defectTypeRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	ProjectTypeRepository projectTypeRepository;

	@Autowired
	ReleasesRepository releasesRepository;

	@Autowired
	SubmoduleRepository submoduleRepository;

//	Save defect implementation
	@Override
	public void saveDefect(DefectDto defectDto) {
		if (validateField(defectDto)) {

			logger.info("save defect started");
			Defect defect = new Defect();
			defect.setStepToRecreate(defectDto.getStepToRecreate());
			defect.setComment(defectDto.getComment());
			defect.setAttcahedment(defectDto.getAttcahedment());
			defect.setCreativeUserId(defectDto.getCreativeUserId());
			defect.setDescription(defectDto.getDescription());
			defect.setAssignedUserId(defectDto.getAssignedUserId());

			Severity severity = severityRepository.getOne(defectDto.getSeverityId());
			defect.setSeverity(severity);

			Priority priority = priorityRepository.getOne(defectDto.getPriorityId());
			defect.setPriority(priority);

			DefectStatus defectStatus = defectStatusRepository.getOne(defectDto.getStatusId());
			defect.setDefectStatus(defectStatus);

			DefectType defectType = defectTypeRepository.getOne(defectDto.getDefectTypeId());
			defect.setDefectType(defectType);

			Project project = projectRepository.getOne(defectDto.getProjectId());
			defect.setProject(project);

			Releases releases = releasesRepository.getOne(defectDto.getReleaseId());
			defect.setReleases(releases);

			Submodule submodule = submoduleRepository.getOne(defectDto.getSubModuleId());
			defect.setSubmodule(submodule);

			defectRepository.save(defect);

		}
	}

//	Update Defect Implementation
	@Override
	public String updateDefect(Long id, DefectUpdateDto defectUpdateDto) {
		logger.info("Update defect started");
		if (existsByDefectId(id)) {
			Defect defect = defectRepository.findById(id).get();
			defect.setStepToRecreate(defectUpdateDto.getStepToRecreate());
			defect.setComment(defectUpdateDto.getComment());
			defect.setAttcahedment(defectUpdateDto.getAttcahedment());
			defect.setCreativeUserId(defectUpdateDto.getCreativeUserId());
			defect.setDescription(defectUpdateDto.getDescription());
			defectRepository.save(defect);
			return "Updated";

		}
		logger.info("Update defect failed by ID");
		return "ID not found";

	}

	@Override
	public List<DefectFindAllDto> findAlldefect(Long projectId, Integer pageNumber, String direction, String property,
			String severityName, String priorityName, String defectStatusName, String defectTypeName,
			String subModuleName, String releaseName, Long assignedUserId, Long creativeUserId) {
		logger.info("Defect find all started in service implementation");
		Pageable paging = null;

		if (!severityName.isEmpty()) {
			logger.info("severity not null");
			paging = PageRequest.of(pageNumber, 10);
			return defectRepository
					.findBySeverity(severityRepository.findBySeverityNameAndProject(severityName,
							projectRepository.getOne(projectId)), paging)
					.stream().map(this::convertToDto).collect(Collectors.toList());
		} else if (!subModuleName.isEmpty()) {
			logger.info("submodule not null");
			paging = PageRequest.of(pageNumber, 10);
			return defectRepository.findBySubmodule(submoduleRepository.findBySubModuleName(subModuleName), paging)
					.stream().map(this::convertToDto).collect(Collectors.toList());
		} else if (!priorityName.isEmpty()) {
			logger.info("priority not null");
			paging = PageRequest.of(pageNumber, 10);
			return defectRepository.findByPriority(priorityRepository
					.findByPriorityNameAndProject(priorityName, projectRepository.getOne(projectId)).get(0), paging)
					.stream().map(this::convertToDto).collect(Collectors.toList());
		} else if (!defectStatusName.isEmpty()) {
			logger.info("defect status not null");
			paging = PageRequest.of(pageNumber, 10);
			return defectRepository.findByDefectStatus(defectStatusRepository
					.findByDefectStatusNameAndProject(defectStatusName, projectRepository.getOne(projectId)).get(0),
					paging).stream().map(this::convertToDto).collect(Collectors.toList());
		} else if (!defectTypeName.isEmpty()) {
			logger.info("defect type not null");
			paging = PageRequest.of(pageNumber, 10);
			return defectRepository.findByDefectType(
					defectTypeRepository
							.findByDefectTypeNameAndProject(defectTypeName, projectRepository.getOne(projectId)).get(0),
					paging).stream().map(this::convertToDto).collect(Collectors.toList());
		} else if (!releaseName.isEmpty()) {
			logger.info("release name not null");
			paging = PageRequest.of(pageNumber, 10);
			return defectRepository
					.findByReleases(releasesRepository.findByReleaseNameContainsIgnoreCaseAndProject(releaseName,
							projectRepository.getOne(projectId)).get(0), paging)
					.stream().map(this::convertToDto).collect(Collectors.toList());
		} else if (assignedUserId != null) {
			logger.info("assign user not null");
			paging = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return defectRepository.findByAssignedUserId(assignedUserId, paging).stream().map(this::convertToDto)
					.collect(Collectors.toList());
		} else if (creativeUserId != null) {
			logger.info("created user not null");
			paging = PageRequest.of(pageNumber, 10, Sort.by(Direction.fromString(direction), property));
			return defectRepository.findByCreativeUserId(creativeUserId, paging).stream().map(this::convertToDto)
					.collect(Collectors.toList());
		}
		
		logger.info("all fields are empty");
		paging = PageRequest.of(pageNumber, 10);
		logger.info("Find all defected fetched");
		return defectRepository.findByProject(projectRepository.getOne(projectId), paging).stream().map(this::convertToDto).collect(Collectors.toList());

	}

	private DefectFindAllDto convertToDto(Defect defect) {
		logger.info("convert DefectDto started ");
		DefectFindAllDto defectFindAllDto = new DefectFindAllDto();
		defectFindAllDto.setDefectId(defect.getDefectId());
		SeverityFindDto severityFindDto = new SeverityFindDto();
		Severity severity = severityRepository.findById(defect.getSeverity().getSeverityId()).get();
		severityFindDto.setSeverityId(severity.getSeverityId());
		severityFindDto.setName(severity.getSeverityName());
		severityFindDto.setColor(severity.getSeverityColor());
		severityFindDto.setSeverityLevel(severity.getSeverityLevel());
		defectFindAllDto.setSeverityFindDto(severityFindDto);
		PriorityFindDto priorityFindDto = new PriorityFindDto();
		Priority priority = priorityRepository.findById(defect.getPriority().getPriorityId()).get();
		priorityFindDto.setPriorityId(priority.getPriorityId());
		priorityFindDto.setName(priority.getPriorityName());
		priorityFindDto.setColor(priority.getPriorityColor());
		priorityFindDto.setPriorityLevel(priority.getPriorityLevel());
		defectFindAllDto.setPriorityFindDto(priorityFindDto);
		defectFindAllDto.setStepToRecreate(defect.getStepToRecreate());
		defectFindAllDto.setComment(defect.getComment());
		defectFindAllDto.setAttcahedment(defect.getAttcahedment());
		defectFindAllDto.setAssignedUserId(defect.getAssignedUserId());
		defectFindAllDto.setCreativeUserId(defect.getCreativeUserId());
		SubmoduleNameDto submoduleNameDto = new SubmoduleNameDto();
		Submodule submodule = submoduleRepository.findById(defect.getSubmodule().getSubModuleId()).get();
		submoduleNameDto.setSubmoduleId(submodule.getSubModuleId());
		submoduleNameDto.setSubmoduleName(submodule.getSubModuleName());
		defectFindAllDto.setSubmoduleNameDto(submoduleNameDto);
		DefectTypeFindDto defectTypeFindDto = new DefectTypeFindDto();
		DefectType defectType = defectTypeRepository.findById(defect.getDefectType().getDefectTypeId()).get();
		defectTypeFindDto.setDefectTypeId(defectType.getDefectTypeId());
		defectTypeFindDto.setDefectTypeName(defectType.getDefectTypeName());
		defectFindAllDto.setDefectTypeFindDto(defectTypeFindDto);
		ReleasesFindAllDto releasesFindAllDto = new ReleasesFindAllDto();
		Releases releases = releasesRepository.findById(defect.getReleases().getReleaseId()).get();
		releasesFindAllDto.setReleaseId(releases.getReleaseId());
		releasesFindAllDto.setDate(releases.getDate());
		releasesFindAllDto.setReleaseStatus(releases.getReleaseStatus());
		releasesFindAllDto.setReleaseName(releases.getReleaseName());
		releasesFindAllDto.setReleaseType(releases.getReleaseType());
		releasesFindAllDto.setReleaseSequence(releases.getReleaseSequence());
		defectFindAllDto.setReleasesFindAllDto(releasesFindAllDto);
		defectFindAllDto.setDescription(defect.getDescription());
		DefectStatusFindAllDto defectStatusFindAllDto = new DefectStatusFindAllDto();
		DefectStatus defectStatus = defectStatusRepository.findById(defect.getDefectStatus().getDefectStatusId()).get();
		defectStatusFindAllDto.setDefectStatusId(defectStatus.getDefectStatusId());
		defectStatusFindAllDto.setStatusName(defectStatus.getDefectStatusName());
		defectStatusFindAllDto.setStatusColor(defectStatus.getDefectStatusColor());
		defectFindAllDto.setDefectStatusFindAllDto(defectStatusFindAllDto);
		return defectFindAllDto;
	}

	@Override
	public boolean validateField(DefectDto defectDto) {

		if (severityRepository.existsBySeverityId(defectDto.getSeverityId())
				&& defectStatusRepository.existsByDefectStatusId(defectDto.getStatusId())
				&& priorityRepository.existsByPriorityId(defectDto.getPriorityId())
				&& defectTypeRepository.existsByDefectTypeId(defectDto.getDefectTypeId())
				&& projectRepository.existsByProjectId(defectDto.getProjectId())
				&& submoduleRepository.existsBySubModuleId(defectDto.getSubModuleId())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean existsByDefectId(Long id) {

		if (defectRepository.existsByDefectId(id)) {
			return true;
		}
		return false;

	}

	@Override
	public DefectFindAllDto findByIdDefect(Long id) {
		logger.info("Find defect started");
		Defect defect = defectRepository.findById(id).get();
		return convertToDto(defect);

	}

	public DefectDto convertDefectDto(Defect defect) {
		DefectDto defectDto = new DefectDto();
		defectDto.setSeverityId(defect.getSeverity().getSeverityId());
		defectDto.setPriorityId(defect.getPriority().getPriorityId());
		defectDto.setStepToRecreate(defect.getStepToRecreate());
		defectDto.setComment(defect.getComment());
		defectDto.setAttcahedment(defect.getAttcahedment());
		defectDto.setAssignedUserId(defect.getAssignedUserId());
		defectDto.setCreativeUserId(defect.getCreativeUserId());
		defectDto.setSubModuleId(defect.getSubmodule().getSubModuleId());
		defectDto.setDefectTypeId(defect.getDefectType().getDefectTypeId());
		defectDto.setReleaseId(defect.getReleases().getReleaseId());
		defectDto.setDescription(defect.getDescription());
		defectDto.setStatusId(defect.getDefectStatus().getDefectStatusId());

		if (!(defect.getProject() == null)) {
			defectDto.setProjectId(defect.getProject().getProjectId());
		}
		return defectDto;
	}

	@Override
	public boolean validateProjectId(Long projectId) {
		if (projectRepository.existsByProjectId(projectId)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean exsitsdelete(Long defectId) {
		if (defectRepository.existsByDefectId(defectId)) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteDefect(Long defectId) {

		defectRepository.deleteById(defectId);

	}

	public DefectSearchDto convertDefectSearchDto(Defect defect) {

		DefectSearchDto defectSearchDto = new DefectSearchDto();
		defectSearchDto.setSeverityId(defect.getSeverity().getSeverityId());
		defectSearchDto.setPriorityId(defect.getPriority().getPriorityId());
		defectSearchDto.setAssignedUserId(defect.getAssignedUserId());
		defectSearchDto.setCreativeUserId(defect.getCreativeUserId());
		defectSearchDto.setSubModuleId(defect.getSubmodule().getSubModuleId());
		defectSearchDto.setDefectTypeId(defect.getDefectType().getDefectTypeId());
		defectSearchDto.setReleaseId(defect.getReleases().getReleaseId());
		defectSearchDto.setDescription(defect.getDescription());
		defectSearchDto.setStatusId(defect.getDefectStatus().getDefectStatusId());

		return defectSearchDto;

	}

	public DefectSearchDto getDefectById(Long id) {
		logger.info("getDefectById method invoked");
		Defect defect = defectRepository.findById(id).get();
		return this.convertDefectSearchDto(defect);
	}

}
