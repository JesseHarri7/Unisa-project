package com.altHealth.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "tblclient_info")
@SQLDelete(sql = "UPDATE tblclient_info set state = 'D', Term_date = CURDATE() WHERE Client_Id = ?")
public class Client implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Client_Id")
	private String clientId;

	@Column(name = "C_Name")
	private String cName;
	
	@Column(name = "C_Surname")
	private String cSurname;
	
	@Column(name = "Address")
	private String address;
	
	@Column(name = "Code")
	private String code;
	
	@Column(name = "C_Tel_H")
	private String cTelH;
	
	@Column(name = "C_Tel_W")
	private String cTelW;
	
	@Column(name = "C_Tel_Cell")
	private String cTelCell;

	@Column(name = "C_Email", unique = true)
	private String cEmail;
	
	@Column(name = "Reference_Id", unique = true)
	private String referenceId;
	
	//Active/Deleted
	@Column(name = "State")
	private String state = "A";  
	
	@Column(name = "Term_date")
	private String termDate;

	public Client() {
	}

	public Client(String clientId, String name, String surname, String address, String code, String email, String refId ) {
		this.clientId = clientId;
		this.cName = name;
		this.cSurname = surname;
		this.address = address;
		this.code = code;
		this.cEmail = email;
		this.referenceId = refId;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
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
		return "Client [clientId=" + clientId + ", cName=" + cName + ", cSurname=" + cSurname
				+ ", address=" + address + ", code=" + code + ", cTelH=" + cTelH + ", cTelW=" + cTelW + ", cTelCell="
				+ cTelCell + ", cEmail=" + cEmail + ", referenceId=" + referenceId + ", state=" + state + ", termDate="
				+ termDate + "]";
	}	

}