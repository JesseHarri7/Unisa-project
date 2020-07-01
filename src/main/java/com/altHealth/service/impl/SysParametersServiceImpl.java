package com.altHealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.entity.SysParameters;
import com.altHealth.repository.SysParametersRepo;
import com.altHealth.service.SysParametersService;

@Service
public class SysParametersServiceImpl implements SysParametersService {

	@Autowired
	SysParametersRepo repo;
	
	@Override
	public SysParameters create(SysParameters entity) {
		SysParameters sysPara = readById(entity.getId());
		if(sysPara == null) {
			return repo.save(entity);
		}else {
			return null;
		}
	}

	@Override
	public SysParameters readById(Integer id) {
		SysParameters sysPara = repo.findSysParametersById(id);
		if (sysPara == null) {
			return null;
		}else {	
			return sysPara;
		}
	}

	@Override
	public List<SysParameters> readAll() {
		return repo.findSysParametersAll();
	}

	@Override
	public SysParameters update(SysParameters entity) {
		SysParameters sysPara = repo.findSysParametersById(entity.getId());
		if (sysPara == null) {
			return null;
		}else {
			return repo.save(entity);
		}
	}

	@Override
	public void delete(SysParameters entity) {
		if (entity != null) {
			repo.delete(entity);
		}
	}

	@Override
	public List<SysParameters> findAllHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysParameters> findSysParametersByVatPercent(Integer vatPercent) {
		List<SysParameters> sysPara = repo.findSysParametersByVatPercent(vatPercent);
		return returnList(sysPara);
	}
	
	private <E> List<E> returnList(List<E> list){
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
	}

	@Override
	public void updateAll(List<SysParameters> entities) {
		repo.saveAll(entities);
	}

}
