package com.altHealth.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.altHealth.entity.Client;
import com.altHealth.entity.Invoice;
import com.altHealth.entity.InvoiceItem;
import com.altHealth.entity.Supplement;

@Component
//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
public class CartModel implements CartModelActivity{
	
	
	Invoice invoice;
	List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
	Client client;
	List<Supplement> supplementList = new ArrayList<Supplement>();
	Double cartTotal;
	Double VAT;

	public CartModel() {}

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

	public List<InvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}

	public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

	public Double getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(Double cartTotal) {
		this.cartTotal = cartTotal;
	}

	public Double getVAT() {
		return VAT;
	}

	public void setVAT(Double vAT) {
		VAT = vAT;
	}

	@Override
	public CartModel getCart() {
		CartModel cart = new CartModel();
		cart.setClient(this.client);
		cart.setSupplementList(this.supplementList);
		cart.setInvoice(this.invoice);
		
		return cart;
	}
	
	public void clearCartSession() {
		this.invoice = new Invoice();
		this.invoiceItems = new ArrayList<InvoiceItem>();
		this.client = new Client();
		this.supplementList = new ArrayList<Supplement>();
		this.cartTotal = 0.0;
		this.VAT = 0.0;
	}
	
}
