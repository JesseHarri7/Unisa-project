package com.altHealth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.altHealth.entity.Supplement;

public interface SupplementRepo extends CrudRepository<Supplement, String> {
	
	Supplement findSupplementBySupplementId(String supplementId);
	
	List<Supplement> findSupplementBySupplierId(String supplierId);

	@Query(value = "SELECT * FROM tblSupplements", nativeQuery = true)
   	List<Supplement> findSupplementAllHistory();
   	
   	@Query(value = "SELECT * FROM tblSupplements WHERE state = 'A'", nativeQuery = true)
   	List<Supplement> findSupplementAll();
}
