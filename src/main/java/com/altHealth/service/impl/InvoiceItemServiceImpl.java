package com.altHealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.entity.InvoiceItem;
import com.altHealth.repository.InvoiceItemRepo;
import com.altHealth.service.InvoiceItemService;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {

	@Autowired
	InvoiceItemRepo repo;
	
	@Override
	public InvoiceItem create(InvoiceItem entity) {
		InvoiceItem invoiceItem = findInvoiceItemByInvNumSupplementId(entity.getInvNum(), entity.getSupplementId());
		if(invoiceItem == null) {
			return repo.save(entity);
		}else {
			return null;
		}
	}

	@Override
	public InvoiceItem readById(String id) {
		return null;
	}

	@Override
	public List<InvoiceItem> readAll() {
		return repo.findInvoiceItemAll();
	}

	@Override
	public InvoiceItem update(InvoiceItem entity) {
		InvoiceItem invoiceItem = findInvoiceItemByInvNumSupplementId(entity.getInvNum(), entity.getSupplementId());
		if(invoiceItem == null) {
			return null;
		}else {	
			return repo.save(entity);
		}
	}

	@Override
	public void delete(InvoiceItem entity) {
		if (entity != null) {
			repo.delete(entity);
		}
	}

	@Override
	public List<InvoiceItem> findAllHistory() {
		return repo.findInvoiceItemAllHistory();
	}

	@Override
	public List<InvoiceItem> findInvoiceItemBySupplementId(String supplementId) {
		List<InvoiceItem> invoiceItems = repo.findInvoiceItemBySupplementId(supplementId);
		return returnList(invoiceItems);
	}
	
	@Override
	public List<InvoiceItem> findInvoiceItemByInvNum(String invNum) {
		List<InvoiceItem> invoiceItems = repo.findInvoiceItemByInvNum(invNum);
		return returnList(invoiceItems);
	}

	@Override
	public InvoiceItem findInvoiceItemByInvNumSupplementId(String invNum, String supplementId) {
		InvoiceItem invoiceItem = repo.findInvoiceItemByInvNumAndSupplementId(invNum, supplementId);
		if(invoiceItem == null) {
			return null;
		}else {
			return invoiceItem;
		}
	}
	
	private <E> List<E> returnList(List<E> list){
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
	}

}
