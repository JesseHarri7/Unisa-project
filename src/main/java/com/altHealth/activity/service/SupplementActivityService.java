package com.altHealth.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.ServiceHelper;
import com.altHealth.Utils.Utils;
import com.altHealth.Utils.Validations;
import com.altHealth.activity.SupplementActiviy;
import com.altHealth.entity.Supplement;
import com.altHealth.entity.Supplier;
import com.altHealth.entity.SysParameters;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.CartModelActivity;
import com.altHealth.model.ReturnModel;

@Service
public class SupplementActivityService implements SupplementActiviy{
	
	@Autowired
	ServiceHelper service;
	@Autowired
	Validations validation;
	@Autowired
	Utils utils;
	@Autowired
	CartModelActivity cart;
	
	@Override
	public ReturnModel formCreateBtn(Supplement supp) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(supp);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		boolean validAdd = doesSupplementExist(supp.getSupplementId(), errorList, idTagList);
		
		boolean isValidSupplierID = doesSupplierIDExist(supp.getSupplierId(), errorList, idTagList);
		
		if(validAdd && isValidSupplierID) {
			updateCostIncl(supp);
			service.getSupplementService().create(supp);
		}
		
		return returnModel;
	}
	
	private boolean doesSupplementExist(String suppId, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		
		Supplement supplement = service.getSupplementService().readById(suppId);
		if(supplement != null) {
			String result = "Supplement: " + suppId + " already exists!";
	    	System.out.println(result);
			errorList.add(result);
			idTagList.add(ModelMappings.SUPPLEMENT_supplementId);
			valid = false;
		}
		
		return valid;
	}
	
	private boolean doesSupplierIDExist(String supplierId, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		
		Supplier supplier = service.getSupplierService().readById(supplierId);
		if(supplier == null) {
			String result = "Supplier: " + supplierId + " does not exist!";
	    	System.out.println(result);
			errorList.add(result);
			idTagList.add(ModelMappings.SUPPLEMENT_supplierId);
			valid = false;
		}
		
		return valid;
	}
	
	private void updateCostIncl(Supplement supp) {
		SysParameters settings = service.getSysParaService().readById(ModelMappings.COMPANY_ID);
		Double costIncl = utils.calcVAT(supp.getCostExcl(), settings.getVatPercent());
		
		supp.setCostIncl(costIncl);
	}
	
}
