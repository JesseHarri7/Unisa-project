package com.altHealth.entity.VO;

import java.io.Serializable;
import java.util.Date;

public class ReportVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Client
	private String clientId;

	private String cName;
	
	private String cSurname;
	
	private String caddress;
	
	private String cCode;
	
	private String cTelH;
	
	private String cTelW;
	
	private String cTelCell;

	private String cEmail;
	
	//Invoice
	private String invNum;
	
	private Date invDate;
	
	private String invPaid;
	
	private Date invPaidDate;
	
	private String invComments;
	
	//InvoiceItems
	private Double itemPrice;
	
	private Integer itemQuantity;
	
	//Reference
	private String referenceId;
	
	private String refDescription;
	
	//Supplement
	private String supplementId;	
	
	private String supplementDescription;
	
	private Double suppCostExcl;
	
	private Double suppCostIncl;
	
	private Integer suppMinLevels;
	
	private Integer suppCurrentStockLevels;
	
	private String suppNappiCode;
	
	//Supplier
	private String supplierId;
	
	private String supplierContactPerson;
	
	private String supplierTel;
	
	private String supplierEmail;
	
	private String supplierBank;
	
	private String supplierBankCode;
	
	private String supplierBankNum;
	
	private String supplierTypeBankAccount;
	
	//Concat fields & funcs
	private String clientName;
	private String supplierinformation;
	private String client;
	private String frequency;
	private String numOfPurchases;
	private String month;

	public ReportVO() {
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcSurname() {
		return cSurname;
	}

	public void setcSurname(String cSurname) {
		this.cSurname = cSurname;
	}

	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public String getcTelH() {
		return cTelH;
	}

	public void setcTelH(String cTelH) {
		this.cTelH = cTelH;
	}

	public String getcTelW() {
		return cTelW;
	}

	public void setcTelW(String cTelW) {
		this.cTelW = cTelW;
	}

	public String getcTelCell() {
		return cTelCell;
	}

	public void setcTelCell(String cTelCell) {
		this.cTelCell = cTelCell;
	}

	public String getcEmail() {
		return cEmail;
	}

	public void setcEmail(String cEmail) {
		this.cEmail = cEmail;
	}

	public String getInvNum() {
		return invNum;
	}

	public void setInvNum(String invNum) {
		this.invNum = invNum;
	}

	public Date getInvDate() {
		return invDate;
	}

	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}

	public String getInvPaid() {
		return invPaid;
	}

	public void setInvPaid(String invPaid) {
		this.invPaid = invPaid;
	}

	public Date getInvPaidDate() {
		return invPaidDate;
	}

	public void setInvPaidDate(Date invPaidDate) {
		this.invPaidDate = invPaidDate;
	}

	public String getInvComments() {
		return invComments;
	}

	public void setInvComments(String invComments) {
		this.invComments = invComments;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getRefDescription() {
		return refDescription;
	}

	public void setRefDescription(String refDescription) {
		this.refDescription = refDescription;
	}

	public String getSupplementId() {
		return supplementId;
	}

	public void setSupplementId(String supplementId) {
		this.supplementId = supplementId;
	}

	public String getSupplementDescription() {
		return supplementDescription;
	}

	public void setSupplementDescription(String supplementDescription) {
		this.supplementDescription = supplementDescription;
	}

	public Double getSuppCostExcl() {
		return suppCostExcl;
	}

	public void setSuppCostExcl(Double suppCostExcl) {
		this.suppCostExcl = suppCostExcl;
	}

	public Double getSuppCostIncl() {
		return suppCostIncl;
	}

	public void setSuppCostIncl(Double suppCostIncl) {
		this.suppCostIncl = suppCostIncl;
	}

	public Integer getSuppMinLevels() {
		return suppMinLevels;
	}

	public void setSuppMinLevels(Integer suppMinLevels) {
		this.suppMinLevels = suppMinLevels;
	}

	public Integer getSuppCurrentStockLevels() {
		return suppCurrentStockLevels;
	}

	public void setSuppCurrentStockLevels(Integer suppCurrentStockLevels) {
		this.suppCurrentStockLevels = suppCurrentStockLevels;
	}

	public String getSuppNappiCode() {
		return suppNappiCode;
	}

	public void setSuppNappiCode(String suppNappiCode) {
		this.suppNappiCode = suppNappiCode;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierContactPerson() {
		return supplierContactPerson;
	}

	public void setSupplierContactPerson(String supplierContactPerson) {
		this.supplierContactPerson = supplierContactPerson;
	}

	public String getSupplierTel() {
		return supplierTel;
	}

	public void setSupplierTel(String supplierTel) {
		this.supplierTel = supplierTel;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public String getSupplierBank() {
		return supplierBank;
	}

	public void setSupplierBank(String supplierBank) {
		this.supplierBank = supplierBank;
	}

	public String getSupplierBankCode() {
		return supplierBankCode;
	}

	public void setSupplierBankCode(String supplierBankCode) {
		this.supplierBankCode = supplierBankCode;
	}

	public String getSupplierBankNum() {
		return supplierBankNum;
	}

	public void setSupplierBankNum(String supplierBankNum) {
		this.supplierBankNum = supplierBankNum;
	}

	public String getSupplierTypeBankAccount() {
		return supplierTypeBankAccount;
	}

	public void setSupplierTypeBankAccount(String supplierTypeBankAccount) {
		this.supplierTypeBankAccount = supplierTypeBankAccount;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSupplierinformation() {
		return supplierinformation;
	}

	public void setSupplierinformation(String supplierinformation) {
		this.supplierinformation = supplierinformation;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getNumOfPurchases() {
		return numOfPurchases;
	}

	public void setNumOfPurchases(String numOfPurchases) {
		this.numOfPurchases = numOfPurchases;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Override
	public String toString() {
		return "ReportVO [clientId=" + clientId + ", cName=" + cName + ", cSurname=" + cSurname + ", caddress="
				+ caddress + ", cCode=" + cCode + ", cTelH=" + cTelH + ", cTelW=" + cTelW + ", cTelCell=" + cTelCell
				+ ", cEmail=" + cEmail + ", invNum=" + invNum + ", invDate=" + invDate + ", invPaid=" + invPaid
				+ ", invPaidDate=" + invPaidDate + ", invComments=" + invComments + ", itemPrice=" + itemPrice
				+ ", itemQuantity=" + itemQuantity + ", referenceId=" + referenceId + ", refDescription="
				+ refDescription + ", supplementId=" + supplementId + ", supplementDescription=" + supplementDescription
				+ ", suppCostExcl=" + suppCostExcl + ", suppCostIncl=" + suppCostIncl + ", suppMinLevels="
				+ suppMinLevels + ", suppCurrentStockLevels=" + suppCurrentStockLevels + ", suppNappiCode="
				+ suppNappiCode + ", supplierId=" + supplierId + ", supplierContactPerson=" + supplierContactPerson
				+ ", supplierTel=" + supplierTel + ", supplierEmail=" + supplierEmail + ", supplierBank=" + supplierBank
				+ ", supplierBankCode=" + supplierBankCode + ", supplierBankNum=" + supplierBankNum
				+ ", supplierTypeBankAccount=" + supplierTypeBankAccount + "]";
	}
	
}
