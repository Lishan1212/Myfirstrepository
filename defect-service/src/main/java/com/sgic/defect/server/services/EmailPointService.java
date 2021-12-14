package com.sgic.defect.server.services;

import com.sgic.defect.server.dto.EmailPointIsActiveDto;

public interface EmailPointService {
	public String updateIsActive(Long Id);
	public void saveEmailPoints(EmailPointIsActiveDto emailPointIsActiveDto);
	boolean errorvalidate(EmailPointIsActiveDto emailPointIsActiveDto);
}
