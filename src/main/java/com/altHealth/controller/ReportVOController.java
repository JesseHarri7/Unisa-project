package com.altHealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.entity.VO.ReportVO;
import com.altHealth.service.impl.ReportVOServiceImpl;

@RestController
@RequestMapping("/report/")
public class ReportVOController {

	@Autowired
	ReportVOServiceImpl service;
	
	@RequestMapping(value = "unpaidInvoices", method = RequestMethod.GET)
	List<ReportVO> unpaidInvoices() {
		return service.unpaidInvoices();
	}
	
	@RequestMapping(value = "birthdaysForToday", method = RequestMethod.GET)
	List<ReportVO> birthdaysForToday() {
		return service.birthdaysForToday();
	}
	
	@RequestMapping(value = "minimumStockLevels", method = RequestMethod.GET)
	List<ReportVO> minimumStockLevels() {
		return service.minimumStockLevels();
	}
	
	@RequestMapping(value = "top10ClientsFor2018and2019", method = RequestMethod.GET)
	List<ReportVO> top10ClientsFor2018and2019() {
		return service.top10ClientsFor2018and2019();
	}
	
	@RequestMapping(value = "purchasesStatistics", method = RequestMethod.GET)
	List<ReportVO> purchasesStatistics() {
		return service.purchasesStatistics();
	}
	
	@RequestMapping(value = "clientInformationQuery", method = RequestMethod.GET)
	List<ReportVO> clientInformationQuery() {
		return service.clientInformationQuery();
	}
	
}
