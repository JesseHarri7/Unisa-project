package com.altHealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.entity.Reference;
import com.altHealth.service.ReferenceService;

@RestController
@RequestMapping("/reference/")
public class ReferenceController implements Controller<Reference, String> {

	@Autowired
	ReferenceService service;
	
	@Override
	public Reference findById(String id) {
		return service.readById(id);
	}

	@Override
	public Reference create(Reference entity) {
		return service.create(entity);
	}

	@Override
	public void update(Reference entity) {
		service.update(entity);
	}

	@Override
	public List<Reference> findAll() {
		return service.readAll();
	}

	@Override
	public List<Reference> findAllHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		Reference entity = service.readById(id);
		if (entity != null) {
			service.delete(entity);
		}
	}

}
