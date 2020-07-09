package com.altHealth.Utils;

import java.util.List;

import com.altHealth.entity.Client;
import com.altHealth.entity.Invoice;
import com.altHealth.entity.InvoiceItem;
import com.altHealth.entity.Supplement;
import com.altHealth.model.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseBody {
	
	@JsonView(Views.Public.class)
	String msg;

	@JsonView(Views.Public.class)
	String status;
	
	@JsonView(Views.Public.class)
	Object result;
	
	@JsonView(Views.Public.class)
	List<String> resultList;
	
	@JsonView(Views.Public.class)
	List<String> idTags;
	
	@JsonView(Views.Public.class)
	Client clientInfo;
	
	@JsonView(Views.Public.class)
	List<Supplement> supplementList;
	
	@JsonView(Views.Public.class)
	Invoice invoiceInfo;
	
	@JsonView(Views.Public.class)
	List<InvoiceItem> invoiceItems;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public List<String> getResultList() {
		return resultList;
	}

	public void setResultList(List<String> list) {
		this.resultList = list;
	}

	public List<String> getIdTags() {
		return idTags;
	}

	public void setIdTags(List<String> idTags) {
		this.idTags = idTags;
	}

	public Client getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(Client clientInfo) {
		this.clientInfo = clientInfo;
	}

	public List<Supplement> getSupplementList() {
		return supplementList;
	}

	public void setSupplementList(List<Supplement> supplementList) {
		this.supplementList = supplementList;
	}

	public Invoice getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(Invoice invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public List<InvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}

	public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

}
