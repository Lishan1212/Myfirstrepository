package com.sgic.internal.eurekaclient.services;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.sgic.internal.eurekaclient.dto.UserDetailsDto;
import com.sgic.internal.eurekaclient.dto.UserDetailsGetDto;

import com.sgic.internal.eurekaclient.entities.UserDetails;
import com.sgic.internal.eurekaclient.repositories.UserDetailsRespository;
import ch.qos.logback.classic.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	Logger logger = (Logger) LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	UserDetailsRespository userDetailsRespository;
	
	@Override
	public List<UserDetailsDto> findAllUserDetails()
	{
		return  userDetailsRespository.findAll().stream().map(this::Convert)
				.collect(Collectors.toList());
	}


	@Override
	public void saveUserDetails(UserDetailsDto userDetailsDto) {
			logger.info("user details save is started in userdatails Service");
			UserDetails userdetails = new UserDetails();
			userdetails.setUserName(userDetailsDto.getUserName());
			userdetails.setUserEmail(userDetailsDto.getUserEmail());
			userdetails.setPassword(userDetailsDto.getPassword());
			userDetailsRespository.save(userdetails);
			logger.info("Module saved successfully");
		}

	private UserDetailsDto Convert(UserDetails userDetails)
	{
		UserDetailsDto userDetailsDto=new UserDetailsDto();
		userDetailsDto.setUserId(userDetails.getUserId());
		userDetailsDto.setUserName(userDetails.getUserName());
		userDetailsDto.setPassword(userDetails.getPassword());
		userDetailsDto.setUserEmail(userDetails.getUserEmail());
		return userDetailsDto;
	}


	@Override
	public void updateuserDetails(Long Id, UserDetailsDto userDetailsDto)
	{
		logger.info("update Started......");
		UserDetails userDetails=userDetailsRespository.findById(Id).get();
		userDetails.setUserId(userDetailsDto.getUserId());
		userDetails.setUserName(userDetailsDto.getUserName());
		userDetails.setUserEmail(userDetailsDto.getUserEmail());
		userDetails.setPassword(userDetailsDto.getPassword());
		userDetailsRespository.save(userDetails);
	}

	@Override
	public UserDetailsGetDto findUserDetailsById(Long userId) {
		UserDetails userdetails = userDetailsRespository.findById(userId).get();
		UserDetailsGetDto userDetailsgetDto = new UserDetailsGetDto();
		userDetailsgetDto.setUserName(userdetails.getUserName());
		userDetailsgetDto.setUserEmail(userdetails.getUserEmail());
		userDetailsgetDto.setPassword(userdetails.getPassword());
		return userDetailsgetDto;
	}


	@Override
	public boolean validateUserId(Long userId) {
		if (userDetailsRespository.existsByUserId(userId)) {
			return true;
		}
		return false;
	}


	
	}
	
	






