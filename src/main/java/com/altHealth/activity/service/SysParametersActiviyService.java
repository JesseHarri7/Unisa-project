package com.altHealth.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.ServiceHelper;
import com.altHealth.Utils.Utils;
import com.altHealth.activity.SysParametersActiviy;
import com.altHealth.entity.ReturnModel;
import com.altHealth.entity.Supplement;
import com.altHealth.entity.SysParameters;
import com.altHealth.mappings.ModelMappings;

@Service
public class SysParametersActiviyService implements SysParametersActiviy {

	@Autowired
	ServiceHelper service;
	@Autowired
	Utils utils;
	
	@Override
	public ReturnModel formUpdateBtn(SysParameters sysPara) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setEntity(sysPara);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		boolean exists = doesSysParaExist(sysPara.getId(), errorList, idTagList);
		
		boolean isValidVatPer = validateVatPer(sysPara.getVatPercent(), errorList, idTagList);
		
		if(exists && isValidVatPer) {
			service.getSysParaService().update(sysPara);
		}
		
		return returnModel;
	}
	
	@Override
	public ReturnModel formUpdateVatBtn() {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		List<Supplement> failedSuppList = new ArrayList<Supplement>();
		List<Supplement> supplementList = service.getSupplementService().readAll();
		SysParameters settings = service.getSysParaService().readById(1);
		
		for(Supplement supp : supplementList) {
			Double costIncl = utils.calcVAT(supp.getCostExcl(), settings.getVatPercent());
			if(costIncl.compareTo(supp.getCostExcl()) < 0) {
				errorList.add("Could not update " + supp.getSupplementId());
				failedSuppList.add(supp);
			}else {
				supp.setCostIncl(costIncl);
			}
		}
		
		supplementList.removeAll(failedSuppList);
		service.getSupplementService().updateAll(supplementList);
		
		return returnModel;
	}
	
	private boolean doesSysParaExist(Integer id, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		
		SysParameters sysPara = service.getSysParaService().readById(id);
		if(sysPara == null) {
			valid = false;
			String result = "Cannot update SysParameter " + id + " does not exist!";
			System.out.print(result);
			errorList.add(result);
		}
		
		return valid;
	}
	
	private boolean validateVatPer(Integer vat, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		
		if(vat < 0) {
			valid = false;
			String result = "Invalid VAT number";
			System.out.print(result);
			errorList.add(result);
			idTagList.add(ModelMappings.SETTINGS_vatPercent);
		}
		
		return valid;
	}

}
