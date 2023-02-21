package com.altHealth.service;

import java.util.List;

import com.altHealth.entity.Supplement;

public interface SupplementService extends Service<Supplement, String> {

	List<Supplement> findSupplementBySupplierId(String supplierId);
	
}
