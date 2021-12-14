package com.sgic.defect.server.entities;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeverityResponse {
	private List<Severity>content;
	int pageNo;
	int pageSize;
	 {
	}

}
