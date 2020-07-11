package com.altHealth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;

import com.altHealth.entity.pk.InvoiceItemPK;

@Entity
@IdClass(InvoiceItemPK.class)
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
	
	@Id
	@Column(name = "Supplement_Id")
	private String supplementId;
	
	@Column(name = "Item_Price")
	private Double itemPrice;
	
	@Column(name = "Item_Quantity")
	private Integer itemQuantity;
	
	@Transient
	private String supplementDescription;
	@Transient
	private Double lineTotal;

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

	public String getSupplementDescription() {
		return supplementDescription;
	}

	public void setSupplementDescription(String supplementDescription) {
		this.supplementDescription = supplementDescription;
	}

	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	@Override
	public String toString() {
		return "InvoiceItem [invNum=" + invNum + ", supplementId=" + supplementId + ", itemPrice=" + itemPrice
				+ ", itemQuantity=" + itemQuantity + ", supplementDescription=" + supplementDescription + ", lineTotal="
				+ lineTotal + "]";
	}
	
}
