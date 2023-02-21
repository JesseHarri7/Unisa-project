package com.altHealth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbluser_role", catalog = "alt_health")
public class UserRole {
	@OneToOne // (fetch = FetchType.EAGER, cascade=CascadeType.ALL)
//	@JoinColumn(name = "email", nullable = false, referencedColumnName= "email")
	@JoinColumn(name = "email", referencedColumnName = "email")
	private User email;

	@Id
	private String role;

	public UserRole() {
	}

	public UserRole(User email, String role) {
		this.email = email;
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User getEmail() {
		return email;
	}

	public void setEmail(User email) {
		this.email = email;
	}
}
