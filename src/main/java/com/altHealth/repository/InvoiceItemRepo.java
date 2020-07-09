package com.altHealth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.altHealth.entity.InvoiceItem;

public interface InvoiceItemRepo extends CrudRepository<InvoiceItem, String> {

	List<InvoiceItem> findByInvNum(String invNum);
	
	List<InvoiceItem> findInvoiceItemBySupplementId(String supplementId);
	
	InvoiceItem findInvoiceItemByInvNumAndSupplementId(String invNum, String supplementId);
	
	@Query(value = "SELECT * FROM tblInv_Items", nativeQuery = true)
   	List<InvoiceItem> findInvoiceItemAllHistory();
   	
   	@Query(value = "SELECT * FROM tblInv_Items WHERE state = 'A'", nativeQuery = true)
   	List<InvoiceItem> findInvoiceItemAll();
	
}
