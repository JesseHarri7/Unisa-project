package com.altHealth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.altHealth.entity.SysParameters;

public interface SysParametersRepo extends CrudRepository<SysParameters, Integer> {
	
	List<SysParameters> findSysParametersByVatPercent(Integer vatPercent);
	
	SysParameters findSysParametersById(Integer id);
	
	@Query(value = "SELECT * FROM tblSysParameters", nativeQuery = true)
	List<SysParameters> findSysParametersAll();
	
}
