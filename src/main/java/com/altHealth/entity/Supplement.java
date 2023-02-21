package com.altHealth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "tblSupplements")
@SQLDelete(sql = "UPDATE tblSupplements set State = 'D', Term_date = CURDATE() WHERE Supplement_Id = ?")
public class Supplement implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Supplement_Id")
	private String supplementId;
	
	@Column(name = "Supplier_Id")
	private String supplierId;
	
	@Column(name = "Supplement_Description")
	private String supplementDescription;
	
	@Column(name = "Cost_Excl")
	private Double costExcl;
	
	@Column(name = "Cost_Incl")
	private Double costIncl;
	
	@Column(name = "Min_Levels")
	private Integer minLevels;
	
	@Column(name = "Current_Stock_Levels")
	private Integer currentStockLevels;
	
	@Column(name = "Nappi_Code")
	private String nappiCode;
	
	@Column(name = "State")
	private String state = "A";
	
	@Column(name = "Term_date")
	private String termDate;

	public Supplement() {
	}

	public Supplement(Long id, String supplementId, String supplierId, String supplementDescription, Double costExcl,
			Double costIncl, Integer minLevels, Integer currentStockLevels, String nappiCode) {
		this.supplementId = supplementId;
		this.supplierId = supplierId;
		this.supplementDescription = supplementDescription;
		this.costExcl = costExcl;
		this.costIncl = costIncl;
		this.minLevels = minLevels;
		this.currentStockLevels = currentStockLevels;
		this.nappiCode = nappiCode;
	}

	public String getSupplementId() {
		return supplementId;
	}

	public void setSupplementId(String supplementId) {
		this.supplementId = supplementId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplementDescription() {
		return supplementDescription;
	}

	public void setSupplementDescription(String supplementDescription) {
		this.supplementDescription = supplementDescription;
	}

	public Double getCostExcl() {
		return costExcl;
	}

	public void setCostExcl(Double costExcl) {
		this.costExcl = costExcl;
	}

	public Double getCostIncl() {
		return costIncl;
	}

	public void setCostIncl(Double costIncl) {
		this.costIncl = costIncl;
	}

	public Integer getMinLevels() {
		return minLevels;
	}

	public void setMinLevels(Integer minLevels) {
		this.minLevels = minLevels;
	}

	public Integer getCurrentStockLevels() {
		return currentStockLevels;
	}

	public void setCurrentStockLevels(Integer currentStockLevels) {
		this.currentStockLevels = currentStockLevels;
	}

	public String getNappiCode() {
		return nappiCode;
	}

	public void setNappiCode(String nappiCode) {
		this.nappiCode = nappiCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTermDate() {
		return termDate;
	}

	public void setTermDate(String termDate) {
		this.termDate = termDate;
	}

	@Override
	public String toString() {
		return "Supplement [supplementId=" + supplementId + ", supplierId=" + supplierId
				+ ", supplementDescription=" + supplementDescription + ", costExcl=" + costExcl + ", costIncl="
				+ costIncl + ", minLevels=" + minLevels + ", currentStockLevels=" + currentStockLevels + ", nappiCode="
				+ nappiCode + ", state=" + state + ", termDate=" + termDate + "]";
	}
	
	
}
