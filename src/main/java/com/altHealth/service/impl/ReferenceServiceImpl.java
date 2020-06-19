package com.altHealth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.entity.Reference;
import com.altHealth.repository.ReferenceRepo;
import com.altHealth.service.ReferenceService;

@Service
public class ReferenceServiceImpl implements ReferenceService {

	@Autowired
	ReferenceRepo repo;
	
	@Override
	public Reference create(Reference entity) {
		Reference ref = readById(entity.getReferenceId());
		if(ref == null) {
			return repo.save(entity);
		}else {
			return null;
		}
	}

	@Override
	public Reference readById(String id) {
		Reference ref = repo.findReferenceByReferenceId(id);
		if(ref == null) {
			return null;
		}else {
			return ref;
		}
	}

	@Override
	public List<Reference> readAll() {
		List<Reference> refList = new ArrayList<Reference>();
		Iterable<Reference> refs = repo.findAll();
		
		for (Reference ref : refs)
		{
			refList.add(ref);
		}
		return refList;
	}

	@Override
	public Reference update(Reference entity) {
		Reference ref = readById(entity.getReferenceId());
		if(ref == null) {
			return null;
		}else {
			return repo.save(entity);
		}
	}

	@Override
	public void delete(Reference entity) {
		if (entity != null) {
			repo.delete(entity);
		}
	}

	@Override
	public List<Reference> findAllHistory() {
		// TODO Auto-generated method stub
		return null;
	}

}
