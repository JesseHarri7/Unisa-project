package com.altHealth.controller.frontendController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.Utils.AjaxResponseBody;
import com.altHealth.activity.MinStockActivity;
import com.altHealth.entity.VO.ReportVO;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.ReturnModel;

@RestController
@RequestMapping("/minStock/")
public class WebMinStockController {

	@Autowired
	MinStockActivity activity;
	
	//create
	@RequestMapping(value = "requestStock", method = RequestMethod.POST)
	public AjaxResponseBody requestStock(@RequestBody List<ReportVO> entities) {
		ReturnModel returnModel = activity.requestStock(entities);
		AjaxResponseBody result = new AjaxResponseBody();
//		Supplement supplement = (Supplement) returnModel.getEntity();
		
		if(returnModel.getErrorList().isEmpty()) {
			result.setStatus(ModelMappings.TRUE);
			result.setMsg("Success! Supplement stock has been requested.");
//			result.setResult(returnModel.getEntity());
		}else {
			result.setStatus(ModelMappings.FALSE);
			result.setMsg("Error! " + returnModel.getStringErrorList());
			result.setResult(returnModel.getErrorList());
		}

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
}
