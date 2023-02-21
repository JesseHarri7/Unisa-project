package com.altHealth.service;

import java.util.List;

import com.altHealth.entity.Supplier;

public interface SupplierService extends Service<Supplier, String> {

	List<Supplier> findSupplierByContactPerson(String contactPerson);
	
	Supplier findSupplierBySupplierEmail(String supplierEmail);
	
	List<Supplier> findSupplierByBank(String bank);
	
	List<Supplier> findSupplierBySupplierTypeBankAccount(String supplierTypeBankAccount);
	
}
