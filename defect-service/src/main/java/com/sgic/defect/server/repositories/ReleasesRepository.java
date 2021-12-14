package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.entities.Releases;

public interface ReleasesRepository extends JpaRepository<Releases, Long> {


	public boolean existsByReleaseId(Long releaseId);


	public List<Releases> findAllReleasesByProject(Project project);

	public boolean existsByProject(Project project);

	public List<Releases> findByReleaseNameContainsIgnoreCase(String releaseName);
	
	public List<Releases> findByReleaseNameContainsIgnoreCaseAndProject(String releaseName, Project project);

	public List<Releases> findByReleaseStatusStartsWithIgnoreCase(String releaseStatus);

	public List<Releases> findByReleaseTypeStartsWithIgnoreCase(String releaseType);

	public List<Releases> findByReleaseSequenceStartsWith(String releaseSequence);

	public List<Releases> findByDate(String date);
}
