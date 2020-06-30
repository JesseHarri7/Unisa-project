package com.altHealth.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "tblinv_info")
@SQLDelete(sql = "UPDATE tblinv_info set state = 'D', Term_date = CURDATE() WHERE Inv_Num = ?")
public class Invoice implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Inv_Num")
	private String invNum;
	
	@Column(name = "Client_Id")
	private String clientId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Inv_Date")
	private Date invDate;
	
	@Column(name = "Inv_Paid")
	private String invPaid;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Inv_Paid_Date")
	private Date invPaidDate;
	
	@Column(name = "Comments")
	private String comments;
	
	@Column(name = "State")
	private String state = "A";
	
	@Column(name = "Term_date")
	private String termDate;

	public Invoice() {
	}
	
	public Invoice(Long id, String invNum, String clientId, Date invDate, String invPaid, Date invPaidDate,
			String comments) {
		this.invNum = invNum;
		this.clientId = clientId;
		this.invDate = invDate;
		this.invPaid = invPaid;
		this.invPaidDate = invPaidDate;
		this.comments = comments;
	}

	public String getInvNum() {
		return invNum;
	}

	public void setInvNum(String invNum) {
		this.invNum = invNum;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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
		return "Invoice [invNum=" + invNum + ", clientId=" + clientId + ", invDate=" + invDate
				+ ", invPaid=" + invPaid + ", invPaidDate=" + invPaidDate + ", comments=" + comments + ", state="
				+ state + ", termDate=" + termDate + "]";
	}
	
}
