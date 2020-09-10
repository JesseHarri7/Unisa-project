package com.altHealth.activity.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.ServiceHelper;
import com.altHealth.Utils.Utils;
import com.altHealth.activity.CartActivity;
import com.altHealth.entity.Client;
import com.altHealth.entity.Invoice;
import com.altHealth.entity.InvoiceItem;
import com.altHealth.entity.Supplement;
import com.altHealth.entity.SysParameters;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.CartModel;
import com.altHealth.model.CartModelActivity;
import com.altHealth.model.HTMLModel;
import com.altHealth.model.ReturnModel;

@Service
public class CartActivityService implements CartActivity {

	@Autowired
	CartModelActivity cart;
	@Autowired
	ServiceHelper service;
	@Autowired
	Utils util;

	@Override
	public ReturnModel addClientToCart(Client client) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(client);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);

		cart.setClient(client);

		return returnModel;
	}

	@Override
	public ReturnModel getClientInfo() {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);

		Client client = cart.getClient();
		returnModel.setEntity(client);

		if (client == null) {
			String result = "Please select a Client";
			System.out.println(result);
			errorList.add(result);
		}

		return returnModel;
	}

	@Override
	public ReturnModel getCartInfo() {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);

		CartModel sessionCart = cart.getCart();
		returnModel.setEntity(sessionCart);

		if (sessionCart.getClient() == null) {
			String result = "Please select a Client";
			System.out.println(result);
			errorList.add(result);
		}
		if (sessionCart.getSupplementList().isEmpty()) {
			String result = "Please add supplement/s to your cart";
			System.out.println(result);
			errorList.add(result);
		}

		//Get Invoice number
		if (sessionCart.getInvoice() == null) {
			Invoice newInvoice = new Invoice();
			String max = service.getInvoiceService().findInvNumByMax();
			max = max.substring(3);
			Integer sum = Math.addExact(Integer.parseInt(max), 1);
			max = sum.toString();
			newInvoice.setInvNum("INV" + max);
			sessionCart.setInvoice(newInvoice);
		}
		
		if (!sessionCart.getSupplementList().isEmpty()) {
			//Do initial cart calcs
			SysParameters settings = service.getSysParaService().readById(ModelMappings.COMPANY_ID);
			Integer vatPercent = settings.getVatPercent();
			Double cartItemsTotal = calcItemTotals(sessionCart.getSupplementList());
			Double vat = util.calcVAT(cartItemsTotal, vatPercent);
			
			sessionCart.setCartTotal(cartItemsTotal);
			sessionCart.setVAT(vat);
		}

		return returnModel;
	}
	
	private Double calcItemTotals(List<Supplement> cartItems) {
		Double total = new Double("0");
		
		for(Supplement cartItem : cartItems) {
			total = Double.sum(total, cartItem.getCostExcl());
		}
		
		return total;
	}

	@Override
	public ReturnModel addSupplementToCart(Supplement supplement) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(supplement);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);

		cart.setSupplement(supplement);

		return returnModel;
	}

	@Override
	public ReturnModel addSupplementsToCart(List<Supplement> supplements) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		returnModel.setEntity(supplements);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		returnModel.setResultList(resultList);

		validateQty(supplements, errorList, resultList);
		
		if(!supplements.isEmpty()) {
			List<Supplement> existingSupps = cart.getSupplementList();
	
			if (!existingSupps.isEmpty()) {
				List<Supplement> suppToAdd = removeDuplicates(existingSupps, supplements, errorList, resultList);
				existingSupps.addAll(suppToAdd);
			} else {
				cart.setSupplementList(supplements);
			}
		}

		return returnModel;
	}

	@Override
	public ReturnModel getSupplements() {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);

		List<Supplement> supplements = cart.getSupplementList();
		returnModel.setEntity(supplements);

		if (supplements.isEmpty()) {
			String result = "Please add supplement/s to your cart";
			System.out.println(result);
			errorList.add(result);
		}

		return returnModel;
	}
	
	private void validateQty(List<Supplement> supplements, List<String> errorList, List<String> resultList) {
		List<Supplement> errSuppList = new ArrayList<Supplement>();
		
		for(Supplement supplement : supplements) {
			if(supplement.getCurrentStockLevels().compareTo(0) == 0) {
				String result = "Supplement: " + supplement.getSupplementId() + " has 0 stock available";
				System.out.println(result);
				errorList.add(result);
				
				errSuppList.add(supplement);
			}
		}
		
		supplements.removeAll(errSuppList);
	}

	private List<Supplement> removeDuplicates(List<Supplement> existingSupps, List<Supplement> supplements,
			List<String> errorList, List<String> resultList) {
		List<Supplement> returnList = new ArrayList<Supplement>();
		List<Supplement> dupList = new ArrayList<Supplement>();
		List<String> existingSuppIds = new ArrayList<String>();

		for (Supplement existingSupp : existingSupps) {
			existingSuppIds.add(existingSupp.getSupplementId());
		}

		for (Supplement supp : supplements) {
			if (existingSuppIds.contains(supp.getSupplementId())) {
				dupList.add(supp);
			}
		}

		if (!dupList.isEmpty()) {
			for (Supplement dupSupp : dupList) {
				String result = "Supplement ID: " + dupSupp.getSupplementId() + " is already in your cart";
				System.out.println(result);
				errorList.add(result);
			}

			supplements.removeAll(dupList);
		}

		if (!supplements.isEmpty()) {
			resultList.add("Success! Supplement/s added to cart!");
		}
		returnList = supplements;

		return returnList;
	}

	@Override
	public ReturnModel removeSupplement(String supplementId) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);

		CartModel sessionCart = cart.getCart();
		returnModel.setEntity(sessionCart);

		Supplement suppToRemove = service.getSupplementService().readById(supplementId);
		boolean found = false;

		if (suppToRemove != null) {
			List<Supplement> currSuppList = cart.getSupplementList();
			for (Supplement supp : currSuppList) {
				if (supp.getSupplementId().equalsIgnoreCase(suppToRemove.getSupplementId())) {
					currSuppList.remove(supp);
					cart.setSupplementList(currSuppList);
					found = true;
					break;
				}
			}
		} else {
			String result = "Supplement ID: " + supplementId + " does not exist!";
			System.out.println(result);
			errorList.add(result);
		}
		if (!found) {
			String result = "Supplement ID: " + supplementId + " does not exist in session cart!";
			System.out.println(result);
			errorList.add(result);
		}

		return returnModel;
	}

	@Override
	public ReturnModel sendPDF(HTMLModel html) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		returnModel.setEntity(html);
		String css = "<link rel=\"stylesheet\" href=\"../unisa-project/src/main/webapp/res/css/cartPDF.css\" />";
		String subject = "Invoice order for: " + html.getInvNum();
		try {
			File htmlFile = util.writeTempHTMLFile(html.getInvNum(), html.getHtml(), css);
			String filePath = util.generatePDFFromHTML(html.getInvNum(), htmlFile);
			util.sendEmailAtt(html.getEmail(), filePath, subject);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnModel;
	}

	@Override
	public ReturnModel sendInvoice(CartModel cartInv) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		returnModel.setEntity(cartInv);
		returnModel.setErrorList(errorList);
		
		//Validate all cart information
		Invoice invoice = cartInv.getInvoice();
		List<InvoiceItem> invoiceItems = cartInv.getInvoiceItems();
		
		if(invoice != null && !invoiceItems.isEmpty()) {
			//Validate invoice fields
			validateInvoiceFields(invoice, invoiceItems, errorList);
			if(!errorList.isEmpty()) {
				return returnModel;				
			}
			
			//Make sure invoice number is unique
			getValidInvNum(invoice, errorList);
			if(!errorList.isEmpty()) {
				return returnModel;				
			}
			
			//Adjust supplement stock
			adjustSupplementStock(invoiceItems, errorList);
			invoice.setInvPaid(ModelMappings.NO);
			
			if(errorList.isEmpty()) {
				//No errors then create Invoice
				createInvoice(invoice, invoiceItems);
				cart.clearCartSession();
			}
			
		}else {
			String result = "Cart Invoice details or Invoice items cannot be empty!";
			System.out.println(result);
			errorList.add(result);
		}
		
		return returnModel;
	}
	
	private void createInvoice(Invoice invoice, List<InvoiceItem> invoiceItems) {		
		for(InvoiceItem invoiceItem : invoiceItems) {
			invoiceItem.setInvNum(invoice.getInvNum());
		}
		service.getInvoiceService().create(invoice);
		service.getInvoiceItemService().create(invoiceItems);
	}
	
	private void getValidInvNum(Invoice invoice, List<String> errorList) {
		Invoice existingInv = service.getInvoiceService().readById(invoice.getInvNum());
		
		if(existingInv != null) {
			String result = "Invoice number is not unique!";
			System.out.println(result);
			errorList.add(result);
		}		
	}
	
	private void adjustSupplementStock(List<InvoiceItem> invoiceItems, List<String> errorList) {
		for(InvoiceItem invoiceItem : invoiceItems) {
			//get fresh supplement
			Supplement supplement = service.getSupplementService().readById(invoiceItem.getSupplementId());
			Integer newStockLevel = Math.subtractExact(supplement.getCurrentStockLevels(), invoiceItem.getItemQuantity());
			
			if(newStockLevel >= 0) {
				supplement.setCurrentStockLevels(newStockLevel);
				service.getSupplementService().update(supplement);
			}else {
				String result = "Stock level below 0!";
				System.out.println(result);
				errorList.add(result);
			}
		}
	}
	
	private void validateInvoiceFields(Invoice invoice, List<InvoiceItem> invoiceItems, List<String> errorList) {
		if(invoice.getInvNum() == null) {
			String result = "Invoice number is empty!";
			System.out.println(result);
			errorList.add(result);
		}
		if(invoice.getClientId() == null) {
			String result = "Client ID is empty!";
			System.out.println(result);
			errorList.add(result);
		}
		if(invoice.getInvDate() == null) {
			String result = "Invoice date is empty!";
			System.out.println(result);
			errorList.add(result);
		}
		
		for(InvoiceItem invoiceItem : invoiceItems) {
			if(invoiceItem.getSupplementId() == null) {
				String result = "Supplement ID is empty!";
				System.out.println(result);
				errorList.add(result);
			}
			if(invoiceItem.getItemQuantity() == null || invoiceItem.getItemQuantity() <= 0) {
				String result = "Item quantity is 0 or below!";
				System.out.println(result);
				errorList.add(result);
			}
		}
	}

}
