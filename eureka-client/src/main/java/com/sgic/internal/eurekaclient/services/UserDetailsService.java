package com.sgic.internal.eurekaclient.services;
import java.util.List;


import com.sgic.internal.eurekaclient.dto.UserDetailsDto;
import com.sgic.internal.eurekaclient.dto.UserDetailsGetDto;

public interface UserDetailsService {
	public void saveUserDetails(UserDetailsDto userDetailsDto);

	List<UserDetailsDto> findAllUserDetails();
	public UserDetailsGetDto findUserDetailsById(Long userId);
	public boolean validateUserId(Long userId);

	void updateuserDetails(Long Id, UserDetailsDto userDetailsDto);

}
