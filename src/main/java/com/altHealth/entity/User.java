/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altHealth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbluser")
//@SQLDelete(sql = "UPDATE user set state = 'D' WHERE user_id = ?")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "Password")
	private String password;

	@Id
	@Column(unique = true)
	private String email;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String state = "A";

	private int enabled = 1;

	public User() {
	}

	public User(String email, String password, String firstN, String lastN) {
		this.email = email;
		this.password = password;
		this.firstName = firstN;
		this.lastName = lastN;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return "User [password=" + password + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", state=" + state + ", enabled=" + enabled + "]";
	}

}
