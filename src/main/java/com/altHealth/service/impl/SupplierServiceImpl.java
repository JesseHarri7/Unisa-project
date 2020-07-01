package com.altHealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.entity.Supplier;
import com.altHealth.repository.SupplierRepo;
import com.altHealth.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	SupplierRepo repo;
	
	@Override
	public Supplier create(Supplier entity) {
		Supplier supp = readById(entity.getSupplierId());
		if(supp == null) {
			return repo.save(entity);
		}else {
			return null;
		}
	}

	@Override
	public Supplier readById(String id) {
		Supplier supp = repo.findSupplierBySupplierId(id);
		if(supp == null) {
			return null;
		}else {
			return supp;
		}
	}

	@Override
	public List<Supplier> readAll() {
		return repo.findSupplierAll();
	}

	@Override
	public Supplier update(Supplier entity) {
		Supplier supp = readById(entity.getSupplierId());
		if(supp == null) {
			return null;
		}else {
			return repo.save(entity);
		}
	}

	@Override
	public void delete(Supplier entity) {
		if (entity != null) {
			repo.delete(entity);
		}
	}

	@Override
	public List<Supplier> findAllHistory() {
		return repo.findSupplierAllHistory();
	}

	@Override
	public List<Supplier> findSupplierByContactPerson(String contactPerson) {
		List<Supplier> supps = findSupplierByContactPerson(contactPerson);
		return returnList(supps);
	}

	@Override
	public Supplier findSupplierBySupplierEmail(String supplierEmail) {
		Supplier supp = findSupplierBySupplierEmail(supplierEmail);
		if(supp == null) {
			return null;
		}else {
			return supp;
		}
	}

	@Override
	public List<Supplier> findSupplierByBank(String bank) {
		List<Supplier> supps = findSupplierByBank(bank);
		return returnList(supps);
	}

	@Override
	public List<Supplier> findSupplierBySupplierTypeBankAccount(String supplierTypeBankAccount) {
		List<Supplier> supps = findSupplierBySupplierTypeBankAccount(supplierTypeBankAccount);
		return returnList(supps);
	}
	
	private <E> List<E> returnList(List<E> list){
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
	}

	@Override
	public void updateAll(List<Supplier> entities) {
		repo.saveAll(entities);
	}

}
