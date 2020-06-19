package com.altHealth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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

	@GeneratedValue
	private Long Id;
	
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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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
		return "Reference [Id=" + Id + ", referenceId=" + referenceId + ", description=" + description + "]";
	}
		
	
}
