package com.sgic.defect.server.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sgic.defect.server.dto.Emaildto;
import com.sgic.defect.server.entities.Email;
import com.sgic.defect.server.repositories.EmailRepository;

@Service
public class EmailServicesimpl implements EmailSevices {

	@Autowired
	EmailRepository emailRepository;

	private final Logger logger = LoggerFactory.getLogger(EmailServicesimpl.class);

	public void saveEmail(Emaildto emaildto) {

		logger.info("save email function");
		if (!errorvalidate(emaildto)) {
			logger.info("Started Save");

			Email email = new Email();
			email.setEmailId(emaildto.getEmailId());
			email.setPassword(emaildto.getPassword());
			email.setHostid(emaildto.getHostid());
			email.setPortnumber(emaildto.getPortnumber());
			email.setProtocol(emaildto.getProtocol());
			email.setIsactive(false);
			emailRepository.save(email);

			logger.info("Saved");

		}

	}

	@Override

	public List<Emaildto> findAllemail(Integer pageNo) {
		Integer pageSize = 5;
		logger.info("Starting the agination for email");
		Pageable paging = PageRequest.of(pageNo, 5);
		Page<Email> emailpaging = emailRepository.findAll(paging);
		List<Email> emails = emailpaging.getContent();
		logger.info("get all the deatils for email");
		return emails.stream().map(this::ConverEmaildto).collect(Collectors.toList());

	}

	private Emaildto ConverEmaildto(Email email) {
		Emaildto emaildto = new Emaildto();
		emaildto.setEmailId(email.getEmailId());
		emaildto.setPassword(email.getPassword());
		emaildto.setHostid(email.getHostid());
		emaildto.setPortnumber(email.getPortnumber());
		emaildto.setProtocol(email.getProtocol());

		return emaildto;
	}

	@Override

	public void updateEmail(Long id, Emaildto emaildto) {
		logger.info("update Stared...");
		Email email = new Email();

		email.setEmailId(emaildto.getEmailId());
		email.setPassword(emaildto.getPassword());
		email.setHostid(emaildto.getHostid());
		email.setPortnumber(email.getPortnumber());
		email.setProtocol(emaildto.getProtocol());
		logger.info("updated the email");
		emailRepository.save(email);

	}

	@Override

	public String saveIsActive(Long Id) {

		Email email = emailRepository.findById(Id).get();
		logger.info("email Activation");

		if (email.isIsactive() == true) {
			email.setIsactive(false);
			logger.info("email is disactive");
			emailRepository.save(email);
			return "Email disactive";
		} else {
			if (!emailRepository.existsByIsactive(true)) {
				email.setIsactive(true);
				logger.info("email is activated");
				emailRepository.save(email);
				return "Email is actived";

			} else {
				logger.info("email is already actiavted");
				return "already one Email is active";
			}
		}

	}

	@Override
	public boolean errorvalidate(Emaildto emaildto) {
		if (emailRepository.existsByEmailId(emaildto.getEmailId())) {
			logger.info("email validation passed");

			return true;
		}
		return false;
	}

	@Override
	public void deleteEmail(Long Id) {

		emailRepository.deleteById(Id);
	}

	@Override
	public List<Emaildto> searchEmail(String emailId, Integer protocol, Long hostid, Integer portnumber) {
		logger.info("Email Parameter passed");
		if (!emailId.isEmpty()) {
			logger.info("Email ID Parameter passed");
			return emailRepository.findByEmailIdContainsIgnoreCase(emailId).stream().map(this::convertEmailDto)
					.collect(Collectors.toList());
		} else if (protocol != null) {
			logger.info("protocol  Parameter passed");
			return emailRepository.findByProtocol(protocol).stream().map(this::convertEmailDto)
					.collect(Collectors.toList());
		} else if (hostid != null) {
			logger.info("host id Parameter passed");
			return emailRepository.findByHostid(hostid).stream().map(this::convertEmailDto)
					.collect(Collectors.toList());
		} else if (portnumber != null) {
			logger.info("portnumber Parameter passed");
			return emailRepository.findByPortnumber(portnumber).stream().map(this::convertEmailDto)
					.collect(Collectors.toList());
		}
		logger.info("all email  details passed");
		return emailRepository.findAll().stream().map(this::convertEmailDto).collect(Collectors.toList());

	}

	public Emaildto convertEmailDto(Email email) {
		logger.info("convert to Email Dto passed");
		Emaildto emailDto = new Emaildto();
		emailDto.setEmailId(email.getEmailId());
		emailDto.setHostid(email.getHostid());
		emailDto.setPortnumber(email.getPortnumber());
		emailDto.setProtocol(email.getProtocol());
		logger.info("convert to Email Dto returned");
		return emailDto;
	}

}
