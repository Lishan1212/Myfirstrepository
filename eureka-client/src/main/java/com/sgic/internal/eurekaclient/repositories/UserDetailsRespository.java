package com.sgic.internal.eurekaclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sgic.internal.eurekaclient.entities.UserDetails;

public interface UserDetailsRespository extends JpaRepository<UserDetails, Long> {

	

	boolean existsByUserId(Long userId);

}
