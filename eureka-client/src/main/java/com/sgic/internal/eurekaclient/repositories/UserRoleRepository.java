package com.sgic.internal.eurekaclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.internal.eurekaclient.entities.Role;
import com.sgic.internal.eurekaclient.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	public boolean existsByRole(Role role);
	

}