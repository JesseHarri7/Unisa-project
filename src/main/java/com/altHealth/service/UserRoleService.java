package com.altHealth.service;

import java.util.List;

import com.altHealth.entity.UserRole;

public interface UserRoleService extends Service<UserRole, String>
{	
	UserRole findByEmail(String user);
	
	UserRole findByRole(String role);
	
	List<UserRole> findAll();
}
