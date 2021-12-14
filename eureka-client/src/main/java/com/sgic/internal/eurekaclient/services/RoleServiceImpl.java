package com.sgic.internal.eurekaclient.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.internal.eurekaclient.dto.RoleDto;
import com.sgic.internal.eurekaclient.entities.Role;
import com.sgic.internal.eurekaclient.repositories.RoleRepository;
import com.sgic.internal.eurekaclient.repositories.UserRoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public void saveRole(RoleDto roleDto) {
		logger.info("Role save impl started");
		Role role = new Role();
		role.setRoleName(roleDto.getRoleName());
		roleRepository.save(role);
	}

	@Override
	public String updateRole(Long id, RoleDto roleDto) {
		logger.info("Role update impl started");
		Role role = roleRepository.findById(id).get();
		role.setRoleName(roleDto.getRoleName());
		roleRepository.save(role);
		return "Updated";

	}

	public RoleDto findRoleById(Long id) {
		logger.info("Get findRoleById Impl started");
		Role role = roleRepository.findById(id).get();
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(role.getRoleName());
		return roleDto;

	}

	@Override
	public void deleteRole(Long id) {
		logger.info("Role delete impl started");
		roleRepository.deleteById(id);
		
	}

	@Override
	public List<RoleDto> findAllRole() {
		logger.info("Get all roles impl started");
		return roleRepository.findAll().stream().map(this::convertDto).collect(Collectors.toList());
	}
	
	private RoleDto convertDto(Role role) {

		logger.info("Role convertDto impl started");
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(role.getRoleName());
		return roleDto;
	}
	
	@Override
	public boolean existsRoleName(RoleDto roleDto) {
		if (roleRepository.existsByRoleName(roleDto.getRoleName())) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean existsRoleId(Long id) {
		if(roleRepository.existsByRoleId(id)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean existsRoleInUserRole(Long id) {
		if(userRoleRepository.existsByRole(roleRepository.findById(id).get())) {
			return false;
		}
		return true;
	}

}
