package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.SysParametersActiviy;
import com.altHealth.entity.ReturnModel;
import com.altHealth.entity.SysParameters;
import com.altHealth.mappings.ModelMappings;

@RestController
@RequestMapping("/sysParameters/")
public class WebSysParametersController {
	
	@Autowired
	SysParametersActiviy activity;
	
	@RequestMapping(value = "formUpdateBtn", method = RequestMethod.PUT)
	public AjaxResponseBody formUpdateBtn(@RequestBody SysParameters entity) {
		entity.setId(1);
		ReturnModel returnModel = activity.formUpdateBtn(entity);
		AjaxResponseBody result = new AjaxResponseBody();
		SysParameters sysPara = (SysParameters) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Settings has been updated.");
			result.setResult(sysPara);
		}else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
		}

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	@RequestMapping(value = "formUpdateVatBtn", method = RequestMethod.GET)
	public AjaxResponseBody formUpdateVatBtn() {
		ReturnModel returnModel = activity.formUpdateVatBtn();
		AjaxResponseBody result = new AjaxResponseBody();
		SysParameters sysPara = (SysParameters) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplement Cost incl has been updated.");
			result.setResult(sysPara);
		}else {
			returnModel.getErrorList().add("Warning! Not all Supplements were updated");
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
		}

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	 
}
