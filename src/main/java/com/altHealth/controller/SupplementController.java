package com.altHealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.entity.Supplement;
import com.altHealth.service.SupplementService;

@RestController
@RequestMapping("/supplement/")
public class SupplementController implements Controller<Supplement, String> {

	@Autowired
	SupplementService service;
	
	@Override
	public Supplement findById(String id) {
		return service.readById(id);
	}

	@Override
	public Supplement create(Supplement entity) {
		return service.create(entity);
	}

	@Override
	public void update(Supplement entity) {
		service.update(entity);
	}

	@Override
	public List<Supplement> findAll() {
		return service.readAll();
	}

	@Override
	public List<Supplement> findAllHistory() {
		return service.findAllHistory();
	}

	@Override
	public void delete(String id) {
		Supplement entity = service.readById(id);
		if (entity != null) {
			service.delete(entity);
		}
	}
	
	//findSupplementBySupplierId
	@RequestMapping(value = "findSupplementBySupplierId/{supplierId}", method = RequestMethod.GET)
	List<Supplement> findSupplementBySupplierId(@PathVariable String supplierId){
		return service.findSupplementBySupplierId(supplierId);
	}

}
