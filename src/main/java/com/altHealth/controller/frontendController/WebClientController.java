package com.altHealth.controller.frontendController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.ClientActiviy;
import com.altHealth.entity.Client;

@RestController
@RequestMapping("/client/")
public class WebClientController {
	
	@Autowired
	ClientActiviy activity;

	//Find All
	@RequestMapping(value = "findAllTest", method = RequestMethod.GET)
	public List<Client> findAll() {
		return activity.findAll();
	}
	
	//create
	@RequestMapping(value = "formCreateBtn", method = RequestMethod.POST)
//	@ResponseStatus(HttpStatus.CREATED)
	public AjaxResponseBody formCreateBtn(@RequestBody Client entity) throws Exception {
		AjaxResponseBody result = new AjaxResponseBody();
		
//		if (isValidSearchCriteria(search)) {
		String status = activity.formCreateBtn(entity);
		
		result.setCode("200");
		result.setMsg(status);
//		result.setResult(users);
//		} else {
//			result.setCode("400");
//			result.setMsg("Search criteria is empty!");
//		}

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
		
//		String status = activity.formCreateBtn(entity);
//		return status;
	}
	 
}
