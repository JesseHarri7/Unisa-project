package com.altHealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.entity.InvoiceItem;
import com.altHealth.service.InvoiceItemService;

@RestController
@RequestMapping("/invoiceItem/")
public class InvoiceItemController implements Controller<InvoiceItem, String> {

	@Autowired
	InvoiceItemService service;

	@Override
	public InvoiceItem findById(String id) {
		return null;
	}

	@Override
	public InvoiceItem create(InvoiceItem entity) {
		return service.create(entity);
	}

	@Override
	public void update(InvoiceItem entity) {
		service.update(entity);
	}

	@Override
	public List<InvoiceItem> findAll() {
		return service.readAll();
	}

	@Override
	public List<InvoiceItem> findAllHistory() {
		return service.findAllHistory();
	}

	@Override
	public void delete(String id) {
		
	}
	
	//customDelete
	@RequestMapping(value = "delete/InvoiceItem", method = RequestMethod.GET)
	public void deleteInvoiceItem(@RequestBody InvoiceItem entity){
		InvoiceItem invoiceItem = service.findInvoiceItemByInvNumSupplementId(entity.getInvNum(), entity.getSupplementId());
		if (invoiceItem != null) {
			service.delete(invoiceItem);
		}
	}
	
	//findByInvNum
	@RequestMapping(value = "findInvoiceItemByInvNum/{invNum}", method = RequestMethod.GET)
	List<InvoiceItem> findInvoiceItemByInvNum(@PathVariable String invNum){
		return service.findInvoiceItemByInvNum(invNum);
	}
	
	//findBySuppId
	@RequestMapping(value = "findInvoiceItemBySupplementId/{supplementId}", method = RequestMethod.GET)
	List<InvoiceItem> findInvoiceItemBySupplementId(@PathVariable String supplementId){
		return service.findInvoiceItemBySupplementId(supplementId);
	}
	
	//findByInvNumSuppId
	@RequestMapping(value = "findInvoiceItemByInvNumSupplementId/InvoiceItem", method = RequestMethod.GET)
	InvoiceItem findInvoiceItemByInvNumSupplementId(@RequestBody InvoiceItem entity) {
		return service.findInvoiceItemByInvNumSupplementId(entity.getInvNum(), entity.getSupplementId());
	}
	
}
