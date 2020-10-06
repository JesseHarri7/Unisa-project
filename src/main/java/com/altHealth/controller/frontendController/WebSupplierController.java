package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.SupplierActiviy;
import com.altHealth.entity.Supplier;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.ReturnModel;

@RestController
@RequestMapping("/supplier/")
public class WebSupplierController {
	
	@Autowired
	SupplierActiviy activity;
	
	
	@RequestMapping(value = "formCreateBtn", method = RequestMethod.POST)
	public AjaxResponseBody formCreateBtn(@RequestBody Supplier entity) {
		ReturnModel returnModel = activity.formCreateBtn(entity);
		AjaxResponseBody result = new AjaxResponseBody();
		Supplier supplier = (Supplier) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplier " + supplier.getSupplierId() + " has been created.");
			result.setResult(supplier);
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
	public AjaxResponseBody formUpdateBtn(@RequestBody Supplier entity) {
		ReturnModel returnModel = activity.formUpdateBtn(entity);
		AjaxResponseBody result = new AjaxResponseBody();
		Supplier supplier = (Supplier) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplier " + supplier.getSupplierId() + " has been updated.");
			result.setResult(supplier);
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
