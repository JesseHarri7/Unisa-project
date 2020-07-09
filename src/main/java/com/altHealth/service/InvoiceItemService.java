package com.altHealth.service;

import java.util.List;

import com.altHealth.entity.InvoiceItem;

public interface InvoiceItemService extends Service<InvoiceItem, String> {
	
	List<InvoiceItem> findByInvNum(String invNum);
	
	List<InvoiceItem> findInvoiceItemBySupplementId(String supplementId);
	
	InvoiceItem findInvoiceItemByInvNumSupplementId(String invNum, String supplementId);
	
}
