package com.altHealth.entity.pk;

import java.io.Serializable;

public class InvoiceItemPK  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String invNum;
	
	private String supplementId;

	public InvoiceItemPK() {
	}

	public InvoiceItemPK(String invNum, String supplementId) {
		this.invNum = invNum;
		this.supplementId = supplementId;
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
	
	
}
