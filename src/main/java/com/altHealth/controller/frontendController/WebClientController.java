package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.ClientActiviy;
import com.altHealth.entity.Client;
import com.altHealth.entity.ReturnModel;
import com.altHealth.mappings.ModelMappings;

@RestController
@RequestMapping("/client/")
public class WebClientController {
	
	@Autowired
	ClientActiviy activity;

	/*
	 * //Find All
	 * 
	 * @RequestMapping(value = "findAllTest", method = RequestMethod.GET) public
	 * AjaxResponseBody findAll() { List<Client> clients = activity.findAll();
	 * AjaxResponseBody result = new AjaxResponseBody();
	 * 
	 * if(!clients.isEmpty()) { result.setCode("200");
	 * result.setMsg("Got clients info"); result.setResult(clients); }else {
	 * result.setCode("400"); result.setMsg("No clients info found"); //
	 * result.setResult(clients); } return result; }
	 */
	
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
	 
}
