package com.altHealth.Utils;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

@ManagedBean
public class Utils {

	@Autowired
	ServiceHelper service;
	
	public Double calcVAT(Double value, Integer vatPerc) {
		Double vat = vatPerc.doubleValue() / 100;
		
		Double vatCost = value * vat;
		Double costIncl = value + vatCost;
		
		return costIncl;
	}
	
}
