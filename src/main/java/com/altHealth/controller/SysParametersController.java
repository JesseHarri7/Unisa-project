package com.altHealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.entity.SysParameters;
import com.altHealth.service.SysParametersService;

@RestController
@RequestMapping("/sysParameters/")
public class SysParametersController implements Controller<SysParameters, Integer> {

	@Autowired
	SysParametersService service;
	
	@Override
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public SysParameters findById(@PathVariable Integer id) {
		return service.readById(id);
	}

	@Override
	public SysParameters create(SysParameters entity) {
		entity.setId(1);
		return service.create(entity);
	}

	@Override
	public void update(SysParameters entity) {
		entity.setId(1);
		service.update(entity);
	}

	@Override
	public List<SysParameters> findAll() {
		return service.readAll();
	}

	@Override
	public List<SysParameters> findAllHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		SysParameters entity = service.readById(id);
		if (entity != null) {
			service.delete(entity);
		}		
	}
	
	@RequestMapping(value = "findSysParametersByVarPercent/{vatPercent}", method = RequestMethod.GET)
	public List<SysParameters> findSysParametersByVarPercent(@PathVariable Integer vatPercent){
		return service.findSysParametersByVatPercent(vatPercent);
	}
	
}
