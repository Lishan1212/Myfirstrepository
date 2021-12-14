package com.sgic.internal.eurekaclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.internal.eurekaclient.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public boolean existsByRoleName(String roleName);
	
	public boolean existsByRoleId(Long roleId);
	

}
