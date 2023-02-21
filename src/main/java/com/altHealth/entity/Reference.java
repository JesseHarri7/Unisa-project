package com.altHealth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblReference")
public class Reference implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Reference_Id")
	private String referenceId;
	
	@Column(name = "Description")
	private String description;

	public Reference() {
	}

	public Reference(String referenceId, String description) {
		this.referenceId = referenceId;
		this.description = description;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Reference [referenceId=" + referenceId + ", description=" + description + "]";
	}
		
	
}
