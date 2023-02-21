package com.altHealth.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Service;

import com.altHealth.entity.VO.ReportVO;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.repository.ReportVORepo;

@SuppressWarnings("deprecation")
@Service
public class ReportVOServiceImpl implements ReportVORepo<ReportVO> {

	@PersistenceContext
    private EntityManager entityManager;
	 
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ReportVO> unpaidInvoices(String year) {
		year = year + "-01-01";
        Query nativeQuery = entityManager.createNativeQuery("Select client.client_id as clientId, concat(client.c_name,\" \",client.c_surname) as clientName, inv.inv_num as invNum, inv.inv_date as invDate\r\n" + 
        		"from tblclient_info client, tblinv_info inv\r\n" + 
        		"where client.client_id = inv.client_id and inv.inv_date < ? and inv.inv_paid_date is null\r\n" + 
        		"order by inv_date asc");
        
        nativeQuery.setParameter(1, year);
        nativeQuery.unwrap(SQLQuery.class)
        .addScalar("clientId", StringType.INSTANCE)
        .addScalar("clientName", StringType.INSTANCE)
        .addScalar("invNum", StringType.INSTANCE)
        .addScalar("invDate", DateType.INSTANCE)
        .setResultTransformer(Transformers.aliasToBean(ReportVO.class));
        
        return nativeQuery.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ReportVO> birthdaysForToday() {
		Query nativeQuery = entityManager.createNativeQuery("Select client_id as clientId, concat(c_name,\" \", c_surname) as clientName \r\n" + 
				"from tblclient_info\r\n" + 
				"where DATE_FORMAT(substring(client_id, 1, 6), \"%d %m\") = DATE_FORMAT(CURDATE(), \"%d %m\")");
        
        nativeQuery.unwrap(SQLQuery.class)
        .addScalar("clientId", StringType.INSTANCE)
        .addScalar("clientName", StringType.INSTANCE)
        .setResultTransformer(Transformers.aliasToBean(ReportVO.class));
        
        return nativeQuery.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ReportVO> minimumStockLevels() {
		Query nativeQuery = entityManager.createNativeQuery("select supp.supplement_id as supplementId, concat(supp.supplier_id, \" \", supplier.contact_person, \" \", supplier.supplier_tel) as supplierinformation, \r\n" + 
				"	supp.min_levels as suppMinLevels, supp.Current_Stock_Levels as suppCurrentStockLevels\r\n" + 
				"from tblsupplements supp, tblsupplier_info supplier\r\n" + 
				"where supplier.supplier_id = supp.supplier_id and supp.Current_Stock_Levels < supp.min_levels\r\n" + 
				"order by supp.supplier_id");
        
        nativeQuery.unwrap(SQLQuery.class)
        .addScalar("supplementId", StringType.INSTANCE)
        .addScalar("supplierinformation", StringType.INSTANCE)
        .addScalar("suppMinLevels", IntegerType.INSTANCE)
        .addScalar("suppCurrentStockLevels", IntegerType.INSTANCE)
        .setResultTransformer(Transformers.aliasToBean(ReportVO.class));
        
        return nativeQuery.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ReportVO> top10Clients(String fromDate, String toDate) {
		Query nativeQuery = entityManager.createNativeQuery("select concat(client.client_id, \" \", client.c_name,\" \",client.c_surname) as client, count(inv.client_id) frequency\r\n" + 
				"from tblclient_info client, tblinv_info inv\r\n" + 
				"where client.client_id = inv.client_id and inv.inv_date between ? and ? \r\n" + 
				"group by client.client_id\r\n" + 
				"order by Frequency desc, c_name, c_surname asc\r\n" + 
				"limit 10");
        
		nativeQuery.setParameter(1, fromDate);
		nativeQuery.setParameter(2, toDate);
        nativeQuery.unwrap(SQLQuery.class)
        .addScalar("client", StringType.INSTANCE)
        .addScalar("frequency", StringType.INSTANCE)
        .setResultTransformer(Transformers.aliasToBean(ReportVO.class));
        
        return nativeQuery.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ReportVO> purchasesStatistics(String fromDate, String toDate) {
		Query nativeQuery = entityManager.createNativeQuery("select count(inv_num) as numOfPurchases, monthname(inv_date) as month\r\n" + 
				"from tblinv_info\r\n" + 
				"where inv_date between ? and ? \r\n" + 
				"group by Month\r\n" + 
				"ORDER BY FIELD(MONTH,'January','February','March','April','May', 'June', 'July', 'August','September', 'October', 'November', 'December')");
        
		nativeQuery.setParameter(1, fromDate);
		nativeQuery.setParameter(2, toDate);
        nativeQuery.unwrap(SQLQuery.class)
        .addScalar("numOfPurchases", StringType.INSTANCE)
        .addScalar("month", StringType.INSTANCE)
        .setResultTransformer(Transformers.aliasToBean(ReportVO.class));
        
        return nativeQuery.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ReportVO> clientInformationQuery(List<String> fieldList) {
		String buildWhereClause = useFieldList(fieldList);
		Query nativeQuery = entityManager.createNativeQuery("select client_id as clientId, c_tel_h as cTelH, c_tel_w as cTelW, c_tel_cell as cTelCell, c_email as cEmail\r\n" + 
				"from tblclient_info\r\n" + 
				"where " + buildWhereClause);
        
        nativeQuery.unwrap(SQLQuery.class)
        .addScalar("clientId", StringType.INSTANCE)
        .addScalar("cTelH", StringType.INSTANCE)
        .addScalar("cTelW", StringType.INSTANCE)
        .addScalar("cTelCell", StringType.INSTANCE)
        .addScalar("cEmail", StringType.INSTANCE)
        .setResultTransformer(Transformers.aliasToBean(ReportVO.class));
        
        return nativeQuery.getResultList();
	}
	
	private String useFieldList(List<String> fieldList) {
		String clause = "";
		
		for(String field : fieldList) {
			if(!clause.isEmpty()) {
				clause+= " AND ";
			}
			switch (field) {
			case ModelMappings.CLIENT_Java_cTelH:
				clause+= "(C_Tel_H = '' or C_Tel_H is null)";
				break;
			case ModelMappings.CLIENT_Java_cTelW:
				clause+= "(C_Tel_W = '' or C_Tel_W is null)";
				break;
			case ModelMappings.CLIENT_Java_cTelCell:
				clause+= "(c_tel_cell = '' or c_tel_cell is null)";
				break;
			case ModelMappings.CLIENT_Java_cEmail:
				clause+= "(c_email = '' or c_email is null)";
				break;

			default:
				clause+= "c_tel_cell = '' and c_email = '' or  c_tel_cell is null and c_email is null";
				break;
			}
		}
		
		return clause;
	}

}
