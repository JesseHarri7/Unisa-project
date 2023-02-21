package com.altHealth.activity.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.ServiceHelper;
import com.altHealth.Utils.Utils;
import com.altHealth.activity.MinStockActivity;
import com.altHealth.entity.Supplement;
import com.altHealth.entity.Supplier;
import com.altHealth.entity.VO.ReportVO;
import com.altHealth.model.ReturnModel;

@Service
public class MinStockActivityService implements MinStockActivity {
	
	@Autowired
	ServiceHelper service;
	@Autowired
	Utils utils;

	@Override
	public ReturnModel requestStock(List<ReportVO> reportVO) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		returnModel.setEntity(reportVO);
		returnModel.setErrorList(errorList);
		
		List<Supplement> supplements = convertToSupplements(reportVO);
		sortBySupplierID(supplements);
		
		for(Supplement supplement : supplements) {
			validateSupplement(supplement, errorList);
			validateSupplier(supplement.getSupplierId(), errorList);			
		}
		
		if(errorList.isEmpty()) {
			Map<String, List<Supplement>> supplierSupplementList = new HashMap<>();
			List<String> supplierIDList = new ArrayList<String>();
			
			splitSupplementsBySuppliers(supplements, supplierSupplementList, supplierIDList);
			
			for(String supplierId : supplierIDList) {
				Supplier supplier = service.getSupplierService().readById(supplierId);
				String subject = "AltHealth Supplement restock inquiry";
				String msg = buildMsg(supplierSupplementList.get(supplierId), supplier);
				utils.sendLowStockEmail(supplier.getSupplierEmail(), subject, msg);
			}

		}
					
		
		
		return returnModel;
	}
	
	private void splitSupplementsBySuppliers(List<Supplement> supplements, Map<String, List<Supplement>> supplierSupplementList, 
			List<String> supplierIDList) {
		
		String prevKey = "";
		for(Supplement supplement : supplements) {
			
			if(!supplierIDList.contains(supplement.getSupplierId())) {
				supplierIDList.add(supplement.getSupplierId());
			}
			
			if(!prevKey.equalsIgnoreCase(supplement.getSupplierId())) {
				prevKey = supplement.getSupplierId();
				
				List<Supplement> supplementList = new ArrayList<Supplement>();
				supplementList.add(supplement);
				supplierSupplementList.put(prevKey, supplementList);
			}else {
				supplierSupplementList.get(prevKey).add(supplement);
			}
			
		}
		
	}
	
	private String buildMsg(List<Supplement> supplements, Supplier supplier) {
		String msg = "";
		
		String supplementList = "";
		for(Supplement supplemnt : supplements) {
			supplementList+= supplemnt.getSupplementId() + " " + supplemnt.getSupplementDescription() + "\n";
		}
		
		msg = "Hi " + supplier.getContactPerson() + "\n"
				+ "This is a automated inquiry request to find availability for the following items: \n\n" +
				supplementList;
		
		return msg;
	}
	
	public void sortBySupplierID(List<Supplement> supplements) {
    	Comparator<Supplement> compareBySupplierID = (Supplement o1, Supplement o2) ->
    															o1.getSupplierId().compareTo(o2.getSupplierId());
    															
    	Collections.sort(supplements, compareBySupplierID);
    }
	
	private boolean validateSupplement(Supplement supplement, List<String> errorList) {
		boolean valid = true;
		
		Supplement existingSupp = service.getSupplementService().readById(supplement.getSupplementId());
		if(existingSupp == null) {
			String result = "Supplement: " + supplement.getSupplementId() + " does not exist!";
	    	System.out.println(result);
			errorList.add(result);
			valid = false;
		}
		
		return valid;
	}
	
	private boolean validateSupplier(String supplierId, List<String> errorList) {
		boolean valid = true;
		
		Supplier supplier = service.getSupplierService().readById(supplierId);
		if(supplier == null) {
			String result = "Supplier: " + supplierId + " does not exist!";
	    	System.out.println(result);
			errorList.add(result);
			valid = false;
		}else {
			if(supplier.getSupplierEmail().trim().length() == 0 ) {
				String result = "Supplier: " + supplierId + " does not have a email address!";
		    	System.out.println(result);
				errorList.add(result);
				valid = false;
			}
		}
		
		return valid;
	}
	
	private List<Supplement> convertToSupplements(List<ReportVO> reportVO){
		List<Supplement> supplements = new ArrayList<Supplement>();
		
		for(ReportVO report : reportVO) {
			Supplement supplement = service.getSupplementService().readById(report.getSupplementId());
			if(supplement != null) {
				supplements.add(supplement);
			}
		}
		
		return supplements;
	}

}
