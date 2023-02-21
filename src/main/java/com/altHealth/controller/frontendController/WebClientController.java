package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.CartActivity;
import com.altHealth.activity.ClientActiviy;
import com.altHealth.entity.Client;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.ReturnModel;

@RestController
@RequestMapping("/client/")
public class WebClientController {
	
	@Autowired
	ClientActiviy activity;
	@Autowired
	CartActivity cartActivity;
	
	//create
	@RequestMapping(value = "formCreateBtn", method = RequestMethod.POST)
//	@ResponseStatus(HttpStatus.CREATED)
	public AjaxResponseBody formCreateBtn(@RequestBody Client entity) {		
		ReturnModel returnModel = activity.formCreateBtn(entity);
		AjaxResponseBody result = new AjaxResponseBody();
		Client client = (Client) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Client " + client.getClientId() + " has been created.");
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
	
	@RequestMapping(value = "formUpdateBtn", method = RequestMethod.POST)
	public AjaxResponseBody formUpdateBtn(@RequestBody Client entity) {
		ReturnModel returnModel = activity.formUpdateBtn(entity);
		AjaxResponseBody result = new AjaxResponseBody();
		Client client = (Client) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Client " + client.getClientId() + " has been updated.");
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
	
	@RequestMapping(value = "addClientToCart", method = RequestMethod.POST)
	public AjaxResponseBody addClientToCart(@RequestBody Client entity) {
		ReturnModel returnModel = cartActivity.addClientToCart(entity);
		AjaxResponseBody result = new AjaxResponseBody();
		Client client = (Client) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Client " + client.getClientId() + " added to cart!.");
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
	 
}
