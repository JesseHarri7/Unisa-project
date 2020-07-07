package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.CartActivity;
import com.altHealth.entity.Client;
import com.altHealth.entity.Supplement;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.ReturnModel;

@RestController
@RequestMapping("/cart/")
public class WebCartController {
	
	@Autowired
	CartActivity activity;

	//getSessionClient
	@RequestMapping(value = "getClientInfo", method = RequestMethod.GET)
	public AjaxResponseBody getClientInfo() {
		ReturnModel returnModel = activity.getClientInfo();
		AjaxResponseBody result = new AjaxResponseBody();
		Client client = (Client) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Client " + client.getClientId() + " from session.");
			result.setResult(client);
		}else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
		}
	
		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	//getSessionCartItem
	@RequestMapping(value = "addCartItem", method = RequestMethod.POST)
	public AjaxResponseBody addCartItem(@RequestBody Supplement entity) {
		ReturnModel returnModel = activity.addSupplementToCart(entity);
		AjaxResponseBody result = new AjaxResponseBody();
		Supplement supp = (Supplement) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplement " + supp.getSupplementId() + " added to cart!");
			result.setResult(supp);
		}else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
		}
	
		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	//getSessionCartItems
	@RequestMapping(value = "getCartItems", method = RequestMethod.GET)
	public AjaxResponseBody getCartItems() {
		ReturnModel returnModel = activity.getSupplements();
		AjaxResponseBody result = new AjaxResponseBody();
//		Supplement supps = (Supplement) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Client " + returnModel.getEntity() + " from session.");
			result.setResult(returnModel.getEntity());
//			result.setResultList(returnModel.getEntity());
		}else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
		}
	
		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
}
