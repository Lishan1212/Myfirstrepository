package com.sgic.defect.server.services;

import java.util.List;

import com.sgic.defect.server.dto.Emaildto;

public interface EmailSevices {
	void saveEmail(Emaildto emaildto);

	void updateEmail(Long id, Emaildto emaildto);

	String saveIsActive(Long Id);

	boolean errorvalidate(Emaildto emaildto);

	public void deleteEmail(Long Id);

	List<Emaildto> findAllemail(Integer pageNo);


	public List<Emaildto> searchEmail(String emailId, Integer protocol, Long hostid, Integer portnumber);



}
