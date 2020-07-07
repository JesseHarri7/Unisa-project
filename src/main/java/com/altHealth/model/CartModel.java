package com.altHealth.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.altHealth.entity.Client;
import com.altHealth.entity.Invoice;
import com.altHealth.entity.Supplement;

@Component
//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
public class CartModel implements CartModelActivity{
	
	
	Invoice invoice;
	Client client;
	List<Supplement> supplementList = new ArrayList<Supplement>();
	

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Supplement> getSupplementList() {
		return supplementList;
	}

	public void setSupplementList(List<Supplement> supplementList) {
		this.supplementList = supplementList;
	}

	@Override
	public void setSupplement(Supplement supplement) {
		this.supplementList.add(supplement);
		
	}
}
