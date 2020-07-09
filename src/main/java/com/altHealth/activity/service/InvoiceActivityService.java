package com.altHealth.activity.service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.ServiceHelper;
import com.altHealth.Utils.Utils;
import com.altHealth.activity.InvoiceActivity;
import com.altHealth.entity.Client;
import com.altHealth.entity.Invoice;
import com.altHealth.entity.InvoiceItem;
import com.altHealth.entity.Supplement;
import com.altHealth.model.CartModel;
import com.altHealth.model.HTMLModel;
import com.altHealth.model.ReturnModel;

@Service
public class InvoiceActivityService implements InvoiceActivity {
	
	@Autowired
	ServiceHelper service;
	@Autowired
	Utils util;
	
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	
	@Override
	public ReturnModel getInvoiceInfo(String invoiceNum) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);	
		CartModel cart = new CartModel();
		returnModel.setEntity(cart);
		Client client = new Client();
		
		Invoice invoice = service.getInvoiceService().readById(invoiceNum);
		List<InvoiceItem> invoiceItems = service.getInvoiceItemService().findByInvNum(invoiceNum);
		
		if(invoice != null && !invoiceItems.isEmpty()) {
			client = service.getClientService().readById(invoice.getClientId());
			
			for(InvoiceItem invoiceItem : invoiceItems) {
				Supplement supp = service.getSupplementService().readById(invoiceItem.getSupplementId());
				if(supp != null) {
					invoiceItem.setSupplementDescription(supp.getSupplementDescription());
				}
				calcLineTotal(invoiceItem);
			}
			
			cart.setInvoice(invoice);
			cart.setInvoiceItems(invoiceItems);
			cart.setClient(client);
		}else {
			String result = "Invalid Invoice number: " + invoiceNum;
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
		String css = "<link rel=\"stylesheet\" href=\"../unisa-project/src/main/webapp/res/css/invoicePDF.css\" />";
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
	
	private void calcLineTotal(InvoiceItem invItem) {
		invItem.setLineTotal(Double.valueOf(df2.format((invItem.getItemPrice() * invItem.getItemQuantity()))));
	}

}
