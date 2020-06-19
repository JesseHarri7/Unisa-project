package com.altHealth.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.altHealth.entity.Invoice;

public interface InvoiceRepo extends CrudRepository<Invoice, String> {
    
	Invoice findInvoiceByInvNum(String invNum);
	
	List<Invoice> findInvoiceByClientId(String clientId);
	
	List<Invoice> findInvoiceByInvNumAndClientId(String invNum, String clientId);
	
	List<Invoice> findInvoiceByInvDate(Date invDate);
	
	List<Invoice> findInvoiceByInvPaid(String invPaid);
	
	List<Invoice> findInvoiceByInvPaidDate(Date invPaidDate);
    
    @Query(value = "SELECT * FROM tblinv_info", nativeQuery = true)
   	List<Invoice> findInvoiceAllHistory();
   	
   	@Query(value = "SELECT * FROM tblinv_info WHERE state = 'A'", nativeQuery = true)
   	List<Invoice> findInvoiceAll();
}
