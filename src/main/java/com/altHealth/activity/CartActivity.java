package com.altHealth.activity;

import java.util.List;

import com.altHealth.entity.Client;
import com.altHealth.entity.Supplement;
import com.altHealth.model.CartModel;
import com.altHealth.model.HTMLModel;
import com.altHealth.model.ReturnModel;

public interface CartActivity {
	abstract ReturnModel addClientToCart(Client client);
	abstract ReturnModel getClientInfo();
	
	abstract ReturnModel addSupplementToCart(Supplement supplement);
	abstract ReturnModel addSupplementsToCart(List<Supplement> supplements);
	abstract ReturnModel getSupplements();
	
	abstract ReturnModel getCartInfo();
	
	abstract ReturnModel removeSupplement(String supplementId);
	
	abstract ReturnModel sendPDF(HTMLModel html);
	abstract ReturnModel sendInvoice(CartModel cart);
}
