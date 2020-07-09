package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.InvoiceActivity;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.CartModel;
import com.altHealth.model.HTMLModel;
import com.altHealth.model.ReturnModel;

@RestController
@RequestMapping("/invoice/")
public class WebInvoiceController {
	
	@Autowired
	InvoiceActivity activity;

	// getSessionClient
	@RequestMapping(value = "getInvoiceInfo/{invoiceNum}", method = RequestMethod.GET)
	public AjaxResponseBody getInvoiceInfo(@PathVariable String invoiceNum) {
		ReturnModel returnModel = activity.getInvoiceInfo(invoiceNum);
		AjaxResponseBody result = new AjaxResponseBody();
		CartModel cart = (CartModel) returnModel.getEntity();

		if (returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! " + returnModel.getEntity() + " from session.");
			result.setInvoiceInfo(cart.getInvoice());
			result.setInvoiceItems(cart.getInvoiceItems());
			result.setClientInfo(cart.getClient());
		} else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
		}

		// AjaxResponseBody will be converted into json format and send back to the
		// request.
		return result;
	}
	
	//Send PDF
	@RequestMapping(value = "sendPDF", method = RequestMethod.POST)
	public AjaxResponseBody sendPDF(@RequestBody HTMLModel html) {
		ReturnModel returnModel = activity.sendPDF(html);
		AjaxResponseBody result = new AjaxResponseBody();
		HTMLModel htmlResp = (HTMLModel) returnModel.getEntity();
		
		if (returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Invoice " + htmlResp.getInvNum() + " has been sent.");
		} else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
		}

		// AjaxResponseBody will be converted into json format and send back to the
		// request.
		return result;
	}

}
