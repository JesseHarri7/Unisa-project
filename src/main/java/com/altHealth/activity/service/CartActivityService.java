package com.altHealth.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.activity.CartActivity;
import com.altHealth.entity.Client;
import com.altHealth.entity.Supplement;
import com.altHealth.model.CartModelActivity;
import com.altHealth.model.ReturnModel;

@Service
public class CartActivityService implements CartActivity {

	@Autowired
	CartModelActivity cart;
	
	@Override
	public ReturnModel addClientToCart(Client client) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(client);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		cart.setClient(client);
		
		return returnModel;
	}

	@Override
	public ReturnModel getClientInfo() {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		Client client = cart.getClient();
		returnModel.setEntity(client);
		
		if(client == null) {
			String result = "Please select a Client";
	    	System.out.println(result);
			errorList.add(result);
		}
		
		return returnModel;
	}

	@Override
	public ReturnModel addSupplementToCart(Supplement supplement) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(supplement);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		cart.setSupplement(supplement);
		
		return returnModel;
	}

	@Override
	public ReturnModel addSupplementsToCart(List<Supplement> supplements) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		returnModel.setEntity(supplements);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		returnModel.setResultList(resultList);
		
		List<Supplement> existingSupps = cart.getSupplementList();
		
		if(!existingSupps.isEmpty()) {
			List<Supplement> suppToAdd = validateSuppToAdd(existingSupps, supplements, errorList, resultList);
			existingSupps.addAll(suppToAdd);
		}else {
			cart.setSupplementList(supplements);
		}
		
		return returnModel;
	}

	@Override
	public ReturnModel getSupplements() {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		List<Supplement> supplements = cart.getSupplementList();
		returnModel.setEntity(supplements);
		
		if(supplements.isEmpty()) {
			String result = "Please add supplement/s to your cart";
	    	System.out.println(result);
			errorList.add(result);
		}
		
		return returnModel;
	}
	
	private List<Supplement> validateSuppToAdd(List<Supplement> existingSupps, List<Supplement> supplements, List<String> errorList, List<String> resultList){
		List<Supplement> returnList = new ArrayList<Supplement>();
		List<Supplement> dupList = new ArrayList<Supplement>();
		List<String> existingSuppIds = new ArrayList<String>();
		
		
		for(Supplement existingSupp : existingSupps) {
			existingSuppIds.add(existingSupp.getSupplementId());
		}
		
		for(Supplement supp : supplements) {
			if(existingSuppIds.contains(supp.getSupplementId())) {
				dupList.add(supp);
			}
		}
		
		if(!dupList.isEmpty()) {
			for(Supplement dupSupp : dupList) {
				String result = "Supplement ID: " + dupSupp.getSupplementId() + " is already in your cart";
				System.out.println(result);
				errorList.add(result);
			}
			
			supplements.removeAll(dupList);
		}
		
		if(!supplements.isEmpty()) {
			resultList.add("Success! Supplement/s added to cart!");
		}
		returnList = supplements;		
		
		return returnList;
	}

}
