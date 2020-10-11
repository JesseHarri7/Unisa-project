package com.altHealth.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
		return service.unpaidInvoices("2020");
	}
	
	@RequestMapping(value = "unpaidInvoices/{year}", method = RequestMethod.GET)
	List<ReportVO> unpaidInvoices(@PathVariable String year) {
		return service.unpaidInvoices(year);
	}
	
	@RequestMapping(value = "birthdaysForToday", method = RequestMethod.GET)
	List<ReportVO> birthdaysForToday() {
		return service.birthdaysForToday();
	}
	
	@RequestMapping(value = "minimumStockLevels", method = RequestMethod.GET)
	List<ReportVO> minimumStockLevels() {
		return service.minimumStockLevels();
	}
	
	@RequestMapping(value = "top10Clients", method = RequestMethod.GET)
	List<ReportVO> top10Clients() {	
		DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		LocalDate fromDate = LocalDate.of(2018, 1, 1);
		LocalDate toDate = LocalDate.of(2019, 12, 31);
		
		String fromDateString = FOMATTER.format(fromDate);
		String toDateString = FOMATTER.format(toDate);
		
		return service.top10Clients(fromDateString, toDateString);
	}
	
	@RequestMapping(value = "top10Clients/{fromDate}/{toDate}", method = RequestMethod.GET)
	List<ReportVO> top10Clients(@PathVariable String fromDate, @PathVariable String toDate) {
		return service.top10Clients(fromDate, toDate);
	}
	
	@RequestMapping(value = "purchasesStatistics", method = RequestMethod.GET)
	List<ReportVO> purchasesStatistics() {
		return service.purchasesStatistics();
	}
	
	@RequestMapping(value = "clientInformationQuery", method = RequestMethod.GET)
	List<ReportVO> clientInformationQuery() {
		String[] fieldList = {"cTelCell", "cEmail"};
		return service.clientInformationQuery(Arrays.asList(fieldList));
	}
	
	@RequestMapping(value = "clientInformationQuery/{fieldList}", method = RequestMethod.GET)
	List<ReportVO> clientInformationQuery(@PathVariable List<String> fieldList) {
		return service.clientInformationQuery(fieldList);
	}
	
}
