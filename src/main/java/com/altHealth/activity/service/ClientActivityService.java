package com.altHealth.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.Validations;
import com.altHealth.activity.ClientActiviy;
import com.altHealth.entity.Client;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.CartModelActivity;
import com.altHealth.model.ReturnModel;
import com.altHealth.service.ClientService;

@Service
public class ClientActivityService implements ClientActiviy{

	@Autowired
	ClientService service;
	@Autowired
	Validations validation;
	@Autowired
	CartModelActivity cart;
	
	
	@Override
	public ReturnModel formCreateBtn(Client client) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(client);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		boolean validAdd = doesClientExist(client.getClientId(), errorList, idTagList);
		
		//valid ID date format
		String first6 = client.getClientId().substring(0, 6);
		boolean isValidDateFormat = validation.DateFormatValidation(ModelMappings.DATE_FORMAT, first6, errorList, idTagList, ModelMappings.CLIENT_id);
		boolean isValidID = validation.SAIdNumberValidation(client.getClientId(), errorList, idTagList, ModelMappings.CLIENT_id);
		
		//Validate email
		boolean isValidEmail = validation.emailValidation(client.getcEmail(), errorList, idTagList, ModelMappings.CLIENT_cEmail);
		
		//Validate tel number format
		boolean isValidTelNum = validation.telNumValidation(client.getcTelH(), client.getcTelW(), client.getcTelCell(), errorList, idTagList, ModelMappings.CLIENT_cTelH, ModelMappings.CLIENT_cTelW, ModelMappings.CLIENT_cTelCell);
		
		if(validAdd && isValidDateFormat && isValidID && isValidEmail && isValidTelNum) {
			service.create(client);
		}
		
		return returnModel;
	}
	
	@Override
	public ReturnModel formUpdateBtn(Client client) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(client);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		//Validate email
		boolean isValidEmail = validation.emailValidation(client.getcEmail(), errorList, idTagList, ModelMappings.CLIENT_cEmail);
		
		//Validate tel number format
		boolean isValidTelNum = validation.telNumValidation(client.getcTelH(), client.getcTelW(), client.getcTelCell(), errorList, idTagList, ModelMappings.CLIENT_cTelH, ModelMappings.CLIENT_cTelW, ModelMappings.CLIENT_cTelCell);
		
		if(isValidEmail && isValidTelNum) {
			service.update(client);
		}
		
		return returnModel;
	}
	
	private boolean doesClientExist(String clientId, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		
		Client client = service.readById(clientId);
		if(client != null) {
			String result = "Client: " + clientId + " already exists!";
	    	System.out.println(result);
			errorList.add(result);
			idTagList.add(ModelMappings.CLIENT_id);
			valid = false;
		}
		
		return valid;
	}
	
}
