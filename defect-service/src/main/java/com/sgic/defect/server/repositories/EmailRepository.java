package com.sgic.defect.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.defect.server.entities.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {

	boolean existsByIsactive(boolean isactive);

	boolean existsByEmailId(String emailId);

	public List<Email> findByEmailIdContainsIgnoreCase(String emailId);

	public List<Email> findByProtocol(Integer protocol);

	public List<Email> findByHostid(Long hostid);

	public List<Email> findByPortnumber(int portnumber);

}
