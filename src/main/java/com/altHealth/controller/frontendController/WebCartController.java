package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxCartResponseBody;
import com.altHealth.activity.CartActivity;
import com.altHealth.entity.Supplement;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.CartModel;
import com.altHealth.model.HTMLModel;
import com.altHealth.model.ReturnModel;

@RestController
@RequestMapping("/cart/")
public class WebCartController {

	@Autowired
	CartActivity activity;

	// getSessionClient

	@RequestMapping(value = "getClientInfo", method = RequestMethod.GET)
	public AjaxCartResponseBody getClientInfo() {
		ReturnModel returnModel = activity.getClientInfo();
		AjaxCartResponseBody result = new AjaxCartResponseBody();
		// Client client = (Client) returnModel.getEntity();

		if (returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Client " + returnModel.getEntity() + " from session.");
			result.setResult(returnModel.getEntity());
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

	// getSessionClient
	@RequestMapping(value = "getCartInfo", method = RequestMethod.GET)
	public AjaxCartResponseBody getCartInfo() {
		ReturnModel returnModel = activity.getCartInfo();
		AjaxCartResponseBody result = new AjaxCartResponseBody();
		CartModel cart = (CartModel) returnModel.getEntity();

		if (returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! " + returnModel.getEntity() + " from session.");
			result.setClientInfo(cart.getClient());
			result.setSupplementList(cart.getSupplementList());
			result.setInvoiceInfo(cart.getInvoice());
			result.setCartTotal(cart.getCartTotal());
			result.setVAT(cart.getVAT());
		} else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
			result.setClientInfo(cart.getClient());
			result.setSupplementList(cart.getSupplementList());
			result.setInvoiceInfo(cart.getInvoice());
		}

		// AjaxResponseBody will be converted into json format and send back to the
		// request.
		return result;
	}

	// getSessionCartItem
	@RequestMapping(value = "addCartItem", method = RequestMethod.POST)
	public AjaxCartResponseBody addCartItem(@RequestBody Supplement entity) {
		ReturnModel returnModel = activity.addSupplementToCart(entity);
		AjaxCartResponseBody result = new AjaxCartResponseBody();
		Supplement supp = (Supplement) returnModel.getEntity();

		if (returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplement " + supp.getSupplementId() + " added to cart!");
			result.setResult(supp);
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

	// getSessionCartItems
	@RequestMapping(value = "getCartItems", method = RequestMethod.GET)
	public AjaxCartResponseBody getCartItems() {
		ReturnModel returnModel = activity.getSupplements();
		AjaxCartResponseBody result = new AjaxCartResponseBody();
//		Supplement supps = (Supplement) returnModel.getEntity();

		if (returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Client " + returnModel.getEntity() + " from session.");
			result.setResult(returnModel.getEntity());
//			result.setResultList(returnModel.getEntity());
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
	
	// removeSelectedSupplement
	@RequestMapping(value = "removeSupplement/{supplementId}", method = RequestMethod.POST)
	public AjaxCartResponseBody removeSupplement(@PathVariable String supplementId) {
		ReturnModel returnModel = activity.removeSupplement(supplementId);
		AjaxCartResponseBody result = new AjaxCartResponseBody();
//			Supplement supps = (Supplement) returnModel.getEntity();

		if (returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplement " + supplementId + " removed from session.");
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
	
	//Send PDF
	@RequestMapping(value = "sendPDF", method = RequestMethod.POST)
	public AjaxCartResponseBody sendPDF(@RequestBody HTMLModel html) {
		ReturnModel returnModel = activity.sendPDF(html);
		AjaxCartResponseBody result = new AjaxCartResponseBody();
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
	
	//SendInvoice
	@RequestMapping(value = "sendInvoice", method = RequestMethod.POST)
	public AjaxCartResponseBody sendInvoice(@RequestBody CartModel cart) {
		ReturnModel returnModel = activity.sendInvoice(cart);
		AjaxCartResponseBody result = new AjaxCartResponseBody();
		CartModel cartReturn = (CartModel) returnModel.getEntity();
		
		if (returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Invoice " + cartReturn.getInvoice().getInvNum() + " has been created.");
		} else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
		}

		// AjaxResponseBody will be converted into json format and send back to the
		// request.
		return result;
	}
		
}
