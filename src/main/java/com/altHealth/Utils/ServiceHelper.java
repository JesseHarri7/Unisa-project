package com.altHealth.Utils;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.altHealth.service.ClientService;
import com.altHealth.service.InvoiceItemService;
import com.altHealth.service.InvoiceService;
import com.altHealth.service.ReferenceService;
import com.altHealth.service.SupplementService;
import com.altHealth.service.SupplierService;

@ManagedBean
public class ServiceHelper {
	@Autowired
	ClientService clientService;
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	InvoiceItemService invoiceItemService;
	@Autowired
	ReferenceService refService;
	@Autowired
	SupplementService supplementService;
	@Autowired
	SupplierService supplierService;
	
	public ClientService getClientService() {
		return clientService;
	}
	public InvoiceService getInvoiceService() {
		return invoiceService;
	}
	public InvoiceItemService getInvoiceItemService() {
		return invoiceItemService;
	}
	public ReferenceService getRefService() {
		return refService;
	}
	public SupplementService getSupplementService() {
		return supplementService;
	}
	public SupplierService getSupplierService() {
		return supplierService;
	}
}
