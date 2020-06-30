package com.altHealth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "tblInv_Items")
@SQLDelete(sql = "UPDATE tblInv_Items set state = 'D', Term_date = CURDATE() WHERE Inv_Num = ?")
public class InvoiceItem implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Inv_Num")
	private String invNum;
	
//	@Id
	@Column(name = "Supplement_Id")
	private String supplementId;
	
	@Column(name = "Item_Price")
	private Double itemPrice;
	
	@Column(name = "Item_Quantity")
	private Integer itemQuantity;
	
	@Column(name = "State")
	private String state = "A";
	
	@Column(name = "Term_date")
	private String termDate;

	public InvoiceItem() {
	}

	public InvoiceItem(Long id, String invNum, String supplementId, Double itemPrice, Integer itemQuantity,
			String state, String termDate) {
		this.invNum = invNum;
		this.supplementId = supplementId;
		this.itemPrice = itemPrice;
		this.itemQuantity = itemQuantity;
	}

	public String getInvNum() {
		return invNum;
	}

	public void setInvNum(String invNum) {
		this.invNum = invNum;
	}

	public String getSupplementId() {
		return supplementId;
	}

	public void setSupplementId(String supplementId) {
		this.supplementId = supplementId;
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
		return "InvoiceItem [invNum=" + invNum + ", supplementId=" + supplementId + ", itemPrice="
				+ itemPrice + ", itemQuantity=" + itemQuantity + ", state=" + state + ", termDate=" + termDate + "]";
	}
	
}
