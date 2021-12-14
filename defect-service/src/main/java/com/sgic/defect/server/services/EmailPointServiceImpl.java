package com.sgic.defect.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.EmailPointIsActiveDto;
import com.sgic.defect.server.entities.EmailPoint;
import com.sgic.defect.server.repositories.EmailPointRepository;

@Service
public class EmailPointServiceImpl implements EmailPointService {

	@Autowired
	EmailPointRepository emailPointRepository;
	private final Logger logger = LoggerFactory.getLogger(EmailPointServiceImpl.class);

	public void saveEmailPoints(EmailPointIsActiveDto emailPointIsActiveDto) {

		logger.info("save emailpoint function");
		if (!errorvalidate(emailPointIsActiveDto)) {
			logger.info("Started Save");

			EmailPoint emailpoint = new EmailPoint();
			emailpoint.setPointId(emailPointIsActiveDto.getPointId());
			emailpoint.setPointName(emailPointIsActiveDto.getPointName());
			emailpoint.setActive(false);
			emailPointRepository.save(emailpoint);

			logger.info("Saved");

		}

	}

	@Override
	public boolean errorvalidate(EmailPointIsActiveDto emailPointIsActiveDto) {
		if (emailPointRepository.existsByPointId(emailPointIsActiveDto.getPointId())) {
			logger.info("emailpoint validation passed");

			return true;
		}
		return false;
	}

	@Override

	public String updateIsActive(Long Id) {

		EmailPoint emailpoint = emailPointRepository.findById(Id).get();
		logger.info("emailpoint Activation");

		if (emailpoint.isActive() == true) {
			emailpoint.setActive(false);

			emailPointRepository.save(emailpoint);
			logger.info("emailpoint Activation done");
			return "Emailpoint disactive";
		}
		else {
			emailpoint.setActive(true);
			emailPointRepository.save(emailpoint);
			logger.info("emailpoint Activation ");

			return "Emailpoint active";
		}
		
	}
	


}
