package com.altHealth.service;

import java.util.Date;
import java.util.List;

import com.altHealth.entity.Invoice;

public interface InvoiceService extends Service<Invoice, String> {

	List<Invoice> findInvoiceByClientId(String clientId);
	
	List<Invoice> findInvoiceByInvNumClientId(String invNum, String clientId);
	
	List<Invoice> findInvoiceByInvDate(Date invDate);
	
	List<Invoice> findInvoiceByInvPaid(String invPaid);
	
	List<Invoice> findInvoiceByInvPaidDate(Date invPaidDate);
	
	String findInvNumByMax();
	
}
