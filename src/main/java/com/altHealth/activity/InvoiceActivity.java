package com.altHealth.activity;

import com.altHealth.model.HTMLModel;
import com.altHealth.model.ReturnModel;

public interface InvoiceActivity {
	
	abstract ReturnModel getInvoiceInfo(String invoiceNum);
	
	abstract ReturnModel sendPDF(HTMLModel html);
}
