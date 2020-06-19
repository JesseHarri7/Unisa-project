package com.altHealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.entity.Supplement;
import com.altHealth.repository.SupplementRepo;
import com.altHealth.service.SupplementService;

@Service
public class SupplementServiceImpl implements SupplementService {

	@Autowired
	SupplementRepo repo;
	
	@Override
	public Supplement create(Supplement entity) {
		Supplement supp = readById(entity.getSupplementId());
		if(supp == null) {
			return repo.save(entity);
		}else {
			return null;
		}
	}

	@Override
	public Supplement readById(String id) {
		Supplement supp = repo.findSupplementBySupplementId(id);
		if(supp == null) {
			return null;
		}else {
			return supp;
		}
	}

	@Override
	public List<Supplement> readAll() {
		return repo.findSupplementAll();
	}

	@Override
	public Supplement update(Supplement entity) {
		Supplement supp = readById(entity.getSupplementId());
		if(supp == null) {
			return null;
		}else {
			return repo.save(entity);
		}
	}

	@Override
	public void delete(Supplement entity) {
		if (entity != null) {
			repo.delete(entity);
		}
	}

	@Override
	public List<Supplement> findAllHistory() {
		return repo.findSupplementAllHistory();
	}

	@Override
	public List<Supplement> findSupplementBySupplierId(String supplierId) {
		List<Supplement> supps = findSupplementBySupplierId(supplierId);
		return returnList(supps);
	}
	
	private <E> List<E> returnList(List<E> list){
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
	}

}
