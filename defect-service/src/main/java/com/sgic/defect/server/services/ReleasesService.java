package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.ReleasesDto;
import com.sgic.defect.server.dto.ReleasesFindAllDto;
import com.sgic.defect.server.dto.ReleasesNameDto;

public interface ReleasesService {

	public void saveReleases(ReleasesDto releasesDto);

	public List<ReleasesFindAllDto> findAllReleases(Integer pageNumber, Integer pageSize, String sortBy);

	public List<ReleasesDto> searchRelease(String releaseName, String releaseStatus, String releaseType,
			String releaseSequence, String date);

	public List<ReleasesDto> findAllReleasesByProject(Long id);

	public void updateReleases(Long Id, ReleasesDto releasesDto);

	public ReleasesFindAllDto findReleasesById(Long id);

	public List<ReleasesNameDto> findReleasesNames();

	public boolean existsReleasesId(Long id);

	public boolean existsProjectId(Long projectId);

	boolean isexistById(Long id);

	public void deleteDefectType(Long id);
	
	boolean existsByReleaseInRelease(Long id);

}
