package com.sgic.internal.eurekaclient.services;

import java.util.List;

import com.sgic.internal.eurekaclient.dto.RoleDto;

public interface RoleService {

	public void saveRole(RoleDto roleDto);

	public String updateRole(Long Id, RoleDto roleDto);

	public void deleteRole(Long Id);

	public RoleDto findRoleById(Long id);
	
	public List<RoleDto> findAllRole();
	
	public boolean existsRoleName(RoleDto roleDto);
	
	public boolean existsRoleId(Long id);
	
	public boolean existsRoleInUserRole(Long id);

}
