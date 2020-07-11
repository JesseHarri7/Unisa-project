package com.altHealth.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.entity.Invoice;
import com.altHealth.service.InvoiceService;

@RestController
@RequestMapping("/invoice/")
public class InvoiceController implements Controller<Invoice, String> {

	@Autowired
	InvoiceService service;
	
	@Override
	public Invoice findById(String id) {
		return service.readById(id);
	}

	@Override
	public Invoice create(Invoice entity) {
		return service.create(entity);
	}

	@Override
	public void update(Invoice entity) {
		service.update(entity);
	}

	@Override
	public List<Invoice> findAll() {
		return service.readAll();
	}

	@Override
	public List<Invoice> findAllHistory() {
		return null;
//		return service.findAllHistory();
	}

	@Override
	public void delete(String id) {
		Invoice entity = service.readById(id);
		if (entity != null) {
			service.delete(entity);
		}
	}

	//findByName
	@RequestMapping(value = "findInvoiceByClientId/{clientId}", method = RequestMethod.GET)
	List<Invoice> findInvoiceByClientId(@PathVariable String clientId){
		return service.findInvoiceByClientId(clientId);
	}
	
	//findByInvNumClientId
	@RequestMapping(value = "findInvoiceByInvNumClientId", method = RequestMethod.GET)
	List<Invoice> findInvoiceByInvNumClientId(@RequestBody Invoice entity){
		return service.findInvoiceByInvNumClientId(entity.getInvNum(), entity.getClientId());
	}
	
	//findInvoiceByInvDate
	@RequestMapping(value = "findInvoiceByInvDate/{invDate}", method = RequestMethod.GET)
	List<Invoice> findInvoiceByInvDate(@PathVariable Date invDate){
		return service.findInvoiceByInvDate(invDate);
	}
	
	//findInvoiceByInvPaid
	@RequestMapping(value = "findInvoiceByInvPaid/{invPaid}", method = RequestMethod.GET)
	List<Invoice> findInvoiceByInvPaid(@PathVariable String invPaid){
		return service.findInvoiceByInvPaid(invPaid);
	}
	
	//findInvoiceByInvPaidDate
	@RequestMapping(value = "findInvoiceByInvPaidDate/{invPaidDate}", method = RequestMethod.GET)
	List<Invoice> findInvoiceByInvPaidDate(@PathVariable Date invPaidDate){
		return service.findInvoiceByInvPaidDate(invPaidDate);
	}

}
