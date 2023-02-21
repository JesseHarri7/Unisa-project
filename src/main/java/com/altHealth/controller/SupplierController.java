package com.altHealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.entity.Supplier;
import com.altHealth.service.SupplierService;

@RestController
@RequestMapping("/supplier/")
public class SupplierController implements Controller<Supplier, String> {

	@Autowired
	SupplierService service;

	@Override
	public Supplier findById(String id) {
		return service.readById(id);
	}

	@Override
	public Supplier create(Supplier entity) {
		return service.create(entity);
	}

	@Override
	public void update(Supplier entity) {
		service.update(entity);
	}

	@Override
	public List<Supplier> findAll() {
		return service.readAll();
	}

	@Override
	public List<Supplier> findAllHistory() {
		return service.findAllHistory();
	}

	@Override
	public void delete(String id) {
		Supplier entity = service.readById(id);
		if (entity != null) {
			service.delete(entity);
		}
	}
	
	//findSupplierByContactPerson
	@RequestMapping(value = "findSupplierByContactPerson/{contactPerson}", method = RequestMethod.GET)
	List<Supplier> findSupplierByContactPerson(@PathVariable String contactPerson){
		return service.findSupplierByContactPerson(contactPerson);
	}
	
	//findSupplierBySupplierEmail
	@RequestMapping(value = "findSupplierBySupplierEmail/{supplierEmail}", method = RequestMethod.GET)
	Supplier findSupplierBySupplierEmail(@PathVariable String supplierEmail) {
		return service.findSupplierBySupplierEmail(supplierEmail);
	}
	
	//findSupplierByBank
	@RequestMapping(value = "findSupplierByBank/{bank}", method = RequestMethod.GET)
	List<Supplier> findSupplierByBank(@PathVariable String bank){
		return service.findSupplierByBank(bank);
	}
	
	//findSupplierBySupplierTypeBankAccount
	@RequestMapping(value = "findSupplierBySupplierTypeBankAccount/{supplierTypeBankAccount}", method = RequestMethod.GET)
	List<Supplier> findSupplierBySupplierTypeBankAccount(@PathVariable String supplierTypeBankAccount){
		return service.findSupplierByBank(supplierTypeBankAccount);
	}
}
