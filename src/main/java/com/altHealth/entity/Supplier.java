package com.altHealth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "tblSupplier_Info")
@SQLDelete(sql = "UPDATE tblSupplier_Info set State = 'D', Term_date = CURDATE() WHERE Supplier_Id = ?")
public class Supplier implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Supplier_Id")
	private String supplierId;
	
	@Column(name = "Contact_Person")
	private String contactPerson;
	
	@Column(name = "Supplier_Tel")
	private String supplierTel;
	
	@Column(name = "Supplier_Email")
	private String supplierEmail;
	
	@Column(name = "Bank")
	private String bank;
	
	@Column(name = "Bank_Code")
	private String bankCode;
	
	@Column(name = "Supplier_BankNum")
	private String supplierBankNum;
	
	@Column(name = "Supplier_Type_Bank_Account")
	private String supplierTypeBankAccount;
	
	@Column(name = "State")
	private String state = "A";
	
	@Column(name = "Term_date")
	private String termDate;

	public Supplier() {
	}

	public Supplier(String supplierId, String contactPerson, String supplierTel, String supplierEmail, String bank,
			String bankCode, String supplierBankNum, String supplierTypeBankAccount) {
		this.supplierId = supplierId;
		this.contactPerson = contactPerson;
		this.supplierTel = supplierTel;
		this.supplierEmail = supplierEmail;
		this.bank = bank;
		this.bankCode = bankCode;
		this.supplierBankNum = supplierBankNum;
		this.supplierTypeBankAccount = supplierTypeBankAccount;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
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

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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
		return "Supplier [supplierId=" + supplierId + ", contactPerson=" + contactPerson
				+ ", supplierTel=" + supplierTel + ", supplierEmail=" + supplierEmail + ", bank=" + bank + ", bankCode="
				+ bankCode + ", supplierBankNum=" + supplierBankNum + ", supplierTypeBankAccount="
				+ supplierTypeBankAccount + ", state=" + state + ", termDate=" + termDate + "]";
	}
	
}
