package com.altHealth.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.entity.Invoice;
import com.altHealth.repository.InvoiceRepo;
import com.altHealth.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	InvoiceRepo repo;
	
	@Override
	public Invoice create(Invoice entity) {
		Invoice invoice = readById(entity.getInvNum());
		if(invoice == null) {
			return repo.save(entity);
		}else {
			return null;
		}
	}

	@Override
	public Invoice readById(String id) {
		Invoice invoice = repo.findInvoiceByInvNum(id);
		if(invoice == null) {
			return null;
		}else {
			return invoice;
		}
	}

	@Override
	public List<Invoice> readAll() {
		return repo.findInvoiceAll();
	}

	@Override
	public Invoice update(Invoice entity) {
		Invoice invoice = readById(entity.getInvNum());
		if(invoice == null) {
			return null;
		}else {
			return repo.save(entity);
		}
	}

	@Override
	public void delete(Invoice entity) {
		if (entity != null) {
			repo.delete(entity);
		}
	}

	@Override
	public List<Invoice> findAllHistory() {
		return repo.findInvoiceAllHistory();
	}

	@Override
	public List<Invoice> findInvoiceByClientId(String clientId) {
		List<Invoice> invoices = repo.findInvoiceByClientId(clientId);
		return returnList(invoices);
	}

	@Override
	public List<Invoice> findInvoiceByInvNumClientId(String invNum, String clientId) {
		List<Invoice> invoices = repo.findInvoiceByInvNumAndClientId(invNum, clientId);
		return returnList(invoices);
	}

	@Override
	public List<Invoice> findInvoiceByInvDate(Date invDate) {
		List<Invoice> invoices = repo.findInvoiceByInvDate(invDate);
		return returnList(invoices);
	}

	@Override
	public List<Invoice> findInvoiceByInvPaid(String invPaid) {
		List<Invoice> invoices = repo.findInvoiceByInvPaid(invPaid);
		return returnList(invoices);
	}

	@Override
	public List<Invoice> findInvoiceByInvPaidDate(Date invPaidDate) {
		List<Invoice> invoices = repo.findInvoiceByInvPaidDate(invPaidDate);
		return returnList(invoices);
	}
	
	private <E> List<E> returnList(List<E> list){
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
	}

}
