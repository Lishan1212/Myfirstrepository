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

import com.sgic.defect.server.dto.PriorityDto;
import com.sgic.defect.server.dto.PriorityIdDto;
import com.sgic.defect.server.dto.PriorityNameDto;
import com.sgic.defect.server.entities.Priority;
import com.sgic.defect.server.entities.Project;
import com.sgic.defect.server.repositories.DefectRepository;
import com.sgic.defect.server.repositories.PriorityRepository;
import com.sgic.defect.server.repositories.ProjectRepository;

@Service
public class PriorityServiceImpl implements PriorityService {
	private final Logger logger = LoggerFactory.getLogger(PriorityServiceImpl.class);
	@Autowired
	PriorityRepository priorityRepository;

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	DefectRepository defectRepository;

	@Override
	public List<PriorityIdDto> findAllPriority()  {
		logger.info("get all defect started");
		
		return  priorityRepository.findAll().stream().map(this::ConvertPriorityIdDto)
				.collect(Collectors.toList());

	}

	private PriorityIdDto ConvertPriorityIdDto(Priority priority) {
		logger.info("convertPrioritDto started ");
		PriorityIdDto priorityDtos = new PriorityIdDto();
		priorityDtos.setName(priority.getPriorityName());
		priorityDtos.setColor(priority.getPriorityColor());
		priorityDtos.setPriorityLevel(priority.getPriorityLevel());

		return priorityDtos;
	}

	@Override
	public void savePriority(PriorityDto priorityDto) {
			logger.info("save priority started");
			Priority priority = new Priority();
			priority.setPriorityName(priorityDto.getName());
			priority.setPriorityColor(priorityDto.getColor());
			priority.setPriorityLevel(priorityDto.getPriorityLevel());

			Project project = projectRepository.getOne(priorityDto.getProjectId());
			priority.setProject(project);
			priorityRepository.save(priority);



	}

	@Override
	public void updatePriority(Long Id, PriorityDto PriorityDto) {
		logger.info("update priority started");
		Priority priority = priorityRepository.findById(Id).get();
		priority.setPriorityName(PriorityDto.getName());
		priority.setPriorityColor(PriorityDto.getColor());
		priority.setPriorityLevel(PriorityDto.getPriorityLevel());
		priorityRepository.save(priority);

	}

	@Override
	public List<PriorityNameDto> findAllNamePriority(int pageNumber,int pageSize) {
		logger.info("get all defect name,ID started");
		Pageable paging=PageRequest.of(pageNumber-1, pageSize);

		return priorityRepository.findAll(paging).stream().map(this::convertPriorityNameDto).collect(Collectors.toList());
	}

	private PriorityNameDto convertPriorityNameDto(Priority priority) {
		logger.info("convert PriorityNameDto started ");
		PriorityNameDto priorityNameDto = new PriorityNameDto();
		priorityNameDto.setPriorityId(priority.getPriorityId());
		priorityNameDto.setName(priority.getPriorityName());
		return priorityNameDto;

	}

	private Priority convert(Priority pro) {
		Priority priority = new Priority();
		priority.setPriorityName(pro.getPriorityName());
		priority.setPriorityColor(pro.getPriorityColor());
		return priority;
	}

	public boolean validateduplicate(PriorityDto priorityDto) {
		List<Priority> piroritylist = priorityRepository
				.findByPriorityNameAndProject(priorityDto.getName(),
						projectRepository.findById(priorityDto.getProjectId()).get())
				.stream().map(this::convert).collect(Collectors.toList());
		List<Priority> piroritycolorlist = priorityRepository
				.findByPriorityColorAndProject(priorityDto.getColor(),
						projectRepository.findById(priorityDto.getProjectId()).get())
				.stream().map(this::convert).collect(Collectors.toList());
		if (piroritylist.isEmpty() && piroritycolorlist.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<PriorityDto> getAllPriorityByProject(Long id) {
		Project project = new Project();
		BeanUtils.copyProperties(projectRepository.findById(id).get(), project);
		return priorityRepository.findByProject(project).stream().map(this::ConvertPrioritygetid)
				.collect(Collectors.toList());
	}

	private PriorityDto ConvertPrioritygetid(Priority priority) {
		logger.info("convert PrioritDto started ");
		PriorityDto priorityDtos = new PriorityDto();
		priorityDtos.setName(priority.getPriorityName());
		priorityDtos.setColor(priority.getPriorityColor());
		priorityDtos.setPriorityLevel(priority.getPriorityLevel());
		if (!(priority.getProject() == null)) {
			priorityDtos.setProjectId(priority.getProject().getProjectId());
		}

		return priorityDtos;
	}

	@Override
	public boolean validateprojectid(Long projectId) {
		if (projectRepository.existsById(projectId)) {
			return true;
		}
		return false;
	}

  
	
	@Override
	public List<PriorityDto>prioritySerachByName(String name,String color,String priorityLevel )
	{
		if(!name.isEmpty())
		{
			logger.info("name not blank");
			return priorityRepository.findByPriorityName(name).stream().map(this::ConvertPriority).collect(Collectors.toList());
		}
		
		 if(!color.isEmpty())
		{
			 logger.info("color not blank");
			return priorityRepository.findByPriorityColor(color).stream().map(this::ConvertPriority).collect(Collectors.toList());
		}
		 
		 if(!priorityLevel.isEmpty())
		 {
			 logger.info("priority level  not blank");
			 return priorityRepository.findByPriorityLevel(priorityLevel).stream().map(this::ConvertPriority).collect(Collectors.toList());
		 }
		logger.info("name ,color and prioritylevel are blank");
		return priorityRepository.findAll().stream().map(this::ConvertPriority).collect(Collectors.toList());
		
	}
	
	private PriorityDto ConvertPriority(Priority priority) {
		
		PriorityDto priorityDtos = new PriorityDto();
		priorityDtos.setName(priority.getPriorityName());
		priorityDtos.setColor(priority.getPriorityColor());
		priorityDtos.setPriorityLevel(priority.getPriorityLevel());
		return priorityDtos;
	}
	
	@Override
	public void deletePriority(Long id)
	{
		logger.info("delete the priority");
		priorityRepository.deleteById(id);
	}
	
	@Override
	public boolean existsByPriorityInPriority(Long id)
	{
		Priority  priority=priorityRepository.findById(id).get();
		if(defectRepository.existsByPriority(priority))
		{
			return true;
		}
		return false;
	}
	@Override
	public boolean isExistById(Long id)
	{
		if(priorityRepository.existsByPriorityId(id))
		{
			return true;
		}
		return false;
	}
}
