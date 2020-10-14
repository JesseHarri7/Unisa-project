package com.altHealth.controller.frontendController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.WebBackupRestoreActivity;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.ReturnModel;

@RestController
@RequestMapping("/backupAndRestore/")
public class WebBackupRestoreController {
	
	@Autowired
	WebBackupRestoreActivity activity;
	
	@RequestMapping(value = "findAll", method = RequestMethod.GET)
	public AjaxResponseBody findAll() {
		ReturnModel returnModel = activity.findAll();
		AjaxResponseBody result = new AjaxResponseBody();
//		Supplier supplier = (Supplier) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
//			result.setMsg("Success! Supplier " + supplier.getSupplierId() + " has been created.");
			result.setResultList(returnModel.getResultList());
		}else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
		}

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	@RequestMapping(value = "createBackup", method = RequestMethod.POST)
	public AjaxResponseBody createBackup() {
		ReturnModel returnModel = activity.createBackup();
		AjaxResponseBody result = new AjaxResponseBody();
		String dbFileName = (String) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Backup "+dbFileName+" has been created.");
			result.setResult(dbFileName);
		}else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
		}

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	@RequestMapping(value = "restoreDB/{fileName}", method = RequestMethod.POST)
	public AjaxResponseBody restoreDB(@RequestBody String obj) {
		ReturnModel returnModel = activity.restoreDB(obj);
		AjaxResponseBody result = new AjaxResponseBody();
		String dbFileName = (String) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Backup "+dbFileName+" has been restored.");
			result.setResult(dbFileName);
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
