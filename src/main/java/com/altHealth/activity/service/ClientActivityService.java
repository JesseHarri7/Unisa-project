package com.altHealth.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.Validations;
import com.altHealth.activity.ClientActiviy;
import com.altHealth.entity.Client;
import com.altHealth.entity.ReturnModel;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.service.ClientService;

@Service
public class ClientActivityService implements ClientActiviy{

	@Autowired
	ClientService service;
	@Autowired
	Validations validation;
	
	
	@Override
	public ReturnModel formCreateBtn(Client client) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(client);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		//valid ID date format
		String first6 = client.getClientId().substring(0, 6);
		boolean isValidDateFormat = validation.DateFormatValidation(ModelMappings.DATE_FORMAT, first6, errorList, idTagList);
		boolean isValidID = validation.SAIdNumberValidation(client.getClientId(), errorList, idTagList);
		
		if(isValidDateFormat && isValidID) {
			service.create(client);
		}
		
		return returnModel;
	}

	@Override
	public List<Client> findAll() {
		List<Client> clients = service.readAll();
		if(!clients.isEmpty()) {
			System.out.println("Got clients info");
		}
		return clients;
	}

	
}
