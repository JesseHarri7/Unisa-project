package com.altHealth.controller.frontendController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.CartActivity;
import com.altHealth.activity.SupplementActiviy;
import com.altHealth.entity.Supplement;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.ReturnModel;

@RestController
@RequestMapping("/supplement/")
public class WebSupplementController {
	
	@Autowired
	SupplementActiviy activity;
	@Autowired
	CartActivity cartActivity;
	
	//create
	@RequestMapping(value = "formCreateBtn", method = RequestMethod.POST)
//	@ResponseStatus(HttpStatus.CREATED)
	public AjaxResponseBody formCreateBtn(@RequestBody Supplement entity) {
		ReturnModel returnModel = activity.formCreateBtn(entity);
		AjaxResponseBody result = new AjaxResponseBody();
		Supplement supplement = (Supplement) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplement " + supplement.getSupplementId() + " has been created.");
			result.setResult(supplement);
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
	public AjaxResponseBody formUpdateBtn(@RequestBody Supplement entity) {
		ReturnModel returnModel = activity.formUpdateBtn(entity);
		AjaxResponseBody result = new AjaxResponseBody();
		Supplement supplement = (Supplement) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplement " + supplement.getSupplementId() + " has been updated.");
			result.setResult(supplement);
		}else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
		}

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	//addSessionCartItem
	@RequestMapping(value = "addCartItems", method = RequestMethod.POST)
	public AjaxResponseBody addCartItems(@RequestBody List<Supplement> entities) {
		ReturnModel returnModel = cartActivity.addSupplementsToCart(entities);
		AjaxResponseBody result = new AjaxResponseBody();
//		List<Supplement> supp = (List<Supplement>) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplement/s added to cart!");
			result.setResult(returnModel.getEntity());
		}else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Warning! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
			result.setIdTags(returnModel.getIdTags());
			result.setResultList(returnModel.getResultList());
		}
	
		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	 
}
