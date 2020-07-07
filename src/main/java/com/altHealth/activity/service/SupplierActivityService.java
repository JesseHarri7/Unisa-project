package com.altHealth.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.Validations;
import com.altHealth.activity.SupplierActiviy;
import com.altHealth.entity.Supplier;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.CartModelActivity;
import com.altHealth.model.ReturnModel;
import com.altHealth.service.SupplierService;

@Service
public class SupplierActivityService implements SupplierActiviy{

	@Autowired
	SupplierService service;
	@Autowired
	Validations validation;
	@Autowired
	CartModelActivity cart;
	
	
	@Override
	public ReturnModel formCreateBtn(Supplier supp) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(supp);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		boolean validAdd = doesSupplierExist(supp.getSupplierId(), errorList, idTagList);
		
		//Validate email
		boolean isValidEmail = validation.emailValidation(supp.getSupplierEmail(), errorList, idTagList, ModelMappings.SUPPLIER_supplierEmail);
		
		//Validate tel number format
		boolean isValidTelNum = validation.telNumValidation(supp.getSupplierTel(), null, null, errorList, idTagList, ModelMappings.SUPPLIER_supplierTel);
		
		if(validAdd && isValidEmail && isValidTelNum) {
			service.create(supp);
		}
		
		return returnModel;
	}
	
	private boolean doesSupplierExist(String suppId, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		
		Supplier supplier = service.readById(suppId);
		if(supplier != null) {
			String result = "Supplier: " + suppId + " already exists!";
	    	System.out.println(result);
			errorList.add(result);
			idTagList.add(ModelMappings.SUPPLIER_supplierId);
			valid = false;
		}
		
		return valid;
	}
	
}
