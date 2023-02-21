package com.altHealth.service;

import java.util.List;

import com.altHealth.entity.SysParameters;

public interface SysParametersService extends Service<SysParameters, Integer> {

	List<SysParameters> findSysParametersByVatPercent(Integer vatPercent);
	SysParameters findSysParametersByEmail(String email);
	
}
