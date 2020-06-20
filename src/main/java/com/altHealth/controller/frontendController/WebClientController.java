package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.altHealth.activity.ClientActiviy;
import com.altHealth.entity.Client;

@Controller
@RequestMapping("/client/")
public class WebClientController {
	
	@Autowired
	ClientActiviy activity;

		//create
		@RequestMapping(value = "formCreateBtn", method = RequestMethod.POST)
		@ResponseStatus(HttpStatus.CREATED)
		public void formCreateBtn(@RequestBody Client entity) {
			activity.formCreateBtn(entity); 
		}
	 
}
