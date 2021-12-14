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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.ReleasesDto;
import com.sgic.defect.server.dto.ReleasesFindAllDto;
import com.sgic.defect.server.dto.ReleasesNameDto;
import com.sgic.defect.server.entities.DefectType;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.Releases;
import com.sgic.defect.server.repositories.DefectRepository;
import com.sgic.defect.server.repositories.ProjectRepository;
import com.sgic.defect.server.repositories.ReleasesRepository;

@Service
public class ReleasesServiceImpl implements ReleasesService {

	@Autowired
	ReleasesRepository releasesRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	DefectRepository defectRepository;

	private final Logger logger = LoggerFactory.getLogger(ReleasesServiceImpl.class);

	@Override
	public void saveReleases(ReleasesDto releasesDto) {
		logger.info("Save releases impl started");

		Releases releases = new Releases();
		releases.setReleaseName(releasesDto.getReleaseName());
		releases.setReleaseStatus(releasesDto.getReleaseStatus());
		releases.setReleaseSequence(releasesDto.getReleaseSequence());
		releases.setReleaseType(releasesDto.getReleaseType());
		releases.setDate(releasesDto.getDate());
		releases.setProject(projectRepository.getOne(releasesDto.getProjectId()));
		releasesRepository.save(releases);
	}

	@Override

	public List<ReleasesFindAllDto> findAllReleases(Integer pageNumber, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

		Page<Releases> pageResult = releasesRepository.findAll(paging);
		List<Releases> releaseslist = pageResult.getContent();
		logger.info("FindAll releases impl started");

		return releaseslist.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private ReleasesFindAllDto convertToDto(Releases releases) {
		logger.info("Convert to releasesDto started");

		ReleasesFindAllDto releaseFindAllDto = new ReleasesFindAllDto();
		releaseFindAllDto.setReleaseName(releases.getReleaseName());
		releaseFindAllDto.setReleaseStatus(releases.getReleaseStatus());
		releaseFindAllDto.setReleaseType(releases.getReleaseType());
		releaseFindAllDto.setReleaseSequence(releases.getReleaseSequence());
		releaseFindAllDto.setDate(releases.getDate());
		return releaseFindAllDto;
	}

	@Override
	public List<ReleasesDto> findAllReleasesByProject(Long id) {
		logger.info("FindAllReleasesByProject impl started");

		Project project = new Project();
		BeanUtils.copyProperties(projectRepository.findById(id).get(), project);
		return releasesRepository.findAllReleasesByProject(project).stream().map(this::convertToReleasesDto)
				.collect(Collectors.toList());
	}

	private ReleasesDto convertToReleasesDto(Releases releases) {
		logger.info("Convert to convertToReleasesDto started");

		ReleasesDto releasesDto = new ReleasesDto();
		releasesDto.setReleaseName(releases.getReleaseName());
		releasesDto.setReleaseStatus(releases.getReleaseStatus());
		releasesDto.setReleaseType(releases.getReleaseType());
		releasesDto.setReleaseSequence(releases.getReleaseSequence());
		releasesDto.setDate(releases.getDate());
		releasesDto.setProjectId(releases.getProject().getProjectId());
		return releasesDto;
	}

	@Override
	public void updateReleases(Long Id, ReleasesDto releasesDto) {
		logger.info("Update release impl started");

		Releases releases = releasesRepository.findById(Id).get();
		releases.setReleaseName(releasesDto.getReleaseName());
		releases.setReleaseStatus(releasesDto.getReleaseStatus());
		releases.setReleaseSequence(releasesDto.getReleaseSequence());
		releases.setReleaseType(releasesDto.getReleaseType());
		releases.setDate(releasesDto.getDate());
		releasesRepository.save(releases);
	}

	@Override
	public ReleasesFindAllDto findReleasesById(Long id) {
		logger.info("Find release byId impl started");
		return convertToDto(releasesRepository.findById(id).get());
	}

	@Override
	public List<ReleasesNameDto> findReleasesNames() {
		logger.info("Find releases Names impl started");

		return releasesRepository.findAll().stream().map(this::convertNameToDto).collect(Collectors.toList());
	}

	private ReleasesNameDto convertNameToDto(Releases releases) {
		logger.info("Convert to releaseNameDto started");

		ReleasesNameDto releaseNameDto = new ReleasesNameDto();
		releaseNameDto.setReleaseId(releases.getReleaseId());
		releaseNameDto.setReleaseName(releases.getReleaseName());
		return releaseNameDto;
	}

	@Override
	public boolean existsReleasesId(Long id) {
		if (releasesRepository.existsByReleaseId(id)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean existsProjectId(Long projectId) {
		if (projectRepository.existsByProjectId(projectId)) {
			return true;
		}
		return false;
	}

	@Override
	public List<ReleasesDto> searchRelease(String releaseName, String releaseStatus, String releaseType,
			String releaseSequence, String date) {
		if (!releaseName.isEmpty()) {
			logger.info("release name value passed");
			return releasesRepository.findByReleaseNameContainsIgnoreCase(releaseName).stream()
					.map(this::convertReleaseDto).collect(Collectors.toList());
		} else if (!releaseStatus.isEmpty()) {
			logger.info("release status value passed");
			return releasesRepository.findByReleaseStatusStartsWithIgnoreCase(releaseStatus).stream()
					.map(this::convertReleaseDto).collect(Collectors.toList());
		}

		else if (!(releaseType.isEmpty())) {
			logger.info("release type value passed");
			return releasesRepository.findByReleaseTypeStartsWithIgnoreCase(releaseType).stream()
					.map(this::convertReleaseDto).collect(Collectors.toList());
		} else if (!(releaseSequence.isEmpty())) {
			logger.info("release Sequence value passed");
			return releasesRepository.findByReleaseSequenceStartsWith(releaseSequence).stream()
					.map(this::convertReleaseDto).collect(Collectors.toList());
		} else if (!(date == null)) {
			logger.info("date value passed");
			return releasesRepository.findByDate(date).stream().map(this::convertReleaseDto)
					.collect(Collectors.toList());
		}
		logger.info("all release values passed");
		return releasesRepository.findAll().stream().map(this::convertReleaseDto).collect(Collectors.toList());
	}

	public ReleasesDto convertReleaseDto(Releases releases) {
		logger.info("convert to ReleaseDto started");
		ReleasesDto releasesDto = new ReleasesDto();
		releasesDto.setReleaseName(releases.getReleaseName());
		releasesDto.setReleaseSequence(releases.getReleaseSequence());
		releasesDto.setReleaseStatus(releases.getReleaseType());
		releasesDto.setReleaseType(releases.getReleaseType());
		releasesDto.setDate(releases.getDate());
		releasesDto.setProjectId(releases.getProject().getProjectId());
		logger.info("convert to ReleaseDto returned");
		return releasesDto;

	}

	@Override
	public boolean isexistById(Long id) {
		if (releasesRepository.existsByReleaseId(id)) {
			return true;

		}
		return false;
	}

	@Override
	public void deleteDefectType(Long id) {
		logger.info("delete release start");
		releasesRepository.deleteById(id);

	}

	@Override
	public boolean existsByReleaseInRelease(Long id) {

		Releases releases = releasesRepository.findById(id).get();
		if (defectRepository.existsByReleases(releases)) {
			return true;
		}
		return false;
	}
}
