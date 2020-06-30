package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.SupplierActiviy;
import com.altHealth.entity.ReturnModel;
import com.altHealth.entity.Supplier;
import com.altHealth.mappings.ModelMappings;

@RestController
@RequestMapping("/supplier/")
public class WebSupplierController {
	
	@Autowired
	SupplierActiviy activity;
	
	//create
	@RequestMapping(value = "formCreateBtn", method = RequestMethod.POST)
//	@ResponseStatus(HttpStatus.CREATED)
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
	 
}
