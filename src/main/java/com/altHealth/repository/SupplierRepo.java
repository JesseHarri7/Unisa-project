package com.altHealth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.altHealth.entity.Supplier;

public interface SupplierRepo extends CrudRepository<Supplier, String> {
	
	Supplier findSupplierBySupplierId(String supplierId);
	
	List<Supplier> findSupplierByContactPerson(String contactPerson);
	
	Supplier findSupplierBySupplierEmail(String supplierEmail);
	
	List<Supplier> findSupplierByBank(String bank);
	
	List<Supplier> findSupplierBySupplierTypeBankAccount(String supplierTypeBankAccount);

	@Query(value = "SELECT * FROM tblSupplier_Info", nativeQuery = true)
   	List<Supplier> findSupplierAllHistory();
   	
   	@Query(value = "SELECT * FROM tblSupplier_Info WHERE state = 'A'", nativeQuery = true)
   	List<Supplier> findSupplierAll();
}
