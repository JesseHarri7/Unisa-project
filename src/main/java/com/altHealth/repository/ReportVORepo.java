package com.altHealth.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.altHealth.entity.VO.ReportVO;

@NoRepositoryBean
public interface ReportVORepo<T> {
	
	List<ReportVO> unpaidInvoices();
	
	List<ReportVO> birthdaysForToday();
	
	List<ReportVO> minimumStockLevels ();
	
	List<ReportVO> top10ClientsFor2018and2019();
	
	List<ReportVO> purchasesStatistics();
	
	List<ReportVO> clientInformationQuery();

}
