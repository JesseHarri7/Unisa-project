package com.altHealth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblSysParameters")
public class SysParameters {
	
	@Id
	Integer id;

	@Column(name = "VAT_Percent")
	Integer vatPercent;

	public SysParameters() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVatPercent() {
		return vatPercent;
	}

	public void setVatPercent(Integer vatPercent) {
		this.vatPercent = vatPercent;
	}

	@Override
	public String toString() {
		return "SysParameters [id=" + id + ", vatPercent=" + vatPercent + "]";
	}

}
