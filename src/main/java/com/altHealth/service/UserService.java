package com.altHealth.service;

import java.util.List;

import com.altHealth.entity.User;


public interface UserService extends Service<User, String>
{	
	List<User> findByFirstName(String name);
	
	List<User> findByLastName(String lastName);
	
	User findByEmail(String email);
}
