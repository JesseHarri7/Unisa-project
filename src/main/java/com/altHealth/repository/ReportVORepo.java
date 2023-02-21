package com.altHealth.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.altHealth.entity.VO.ReportVO;

@NoRepositoryBean
public interface ReportVORepo<T> {
	
	List<ReportVO> unpaidInvoices(String year);
	
	List<ReportVO> birthdaysForToday();
	
	List<ReportVO> minimumStockLevels ();
	
	List<ReportVO> top10Clients(String fromDate, String toDate);
	
	List<ReportVO> purchasesStatistics(String fromDate, String toDate);
	
	List<ReportVO> clientInformationQuery(List<String> fieldList);

}
