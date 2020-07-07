package com.altHealth.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.altHealth.entity.UserRole;



public interface UserRoleRepo extends CrudRepository<UserRole, String>  
{	
	UserRole findByEmail(String user);
	
	UserRole findByRole(String role);
	
	@Query(value = "SELECT * FROM tbluser_role", nativeQuery = true)
	List<UserRole> findAll();
}
	