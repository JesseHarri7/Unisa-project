package com.altHealth.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.altHealth.entity.User;


public interface UserRepo extends CrudRepository<User, String>  
{
	List<User> findByFirstName(String name);
	
	List<User> findByLastName(String lastName);
	
    User findByEmail(String email);
    
    @Query(value = "SELECT * FROM tbluser", nativeQuery = true)
	List<User> findAllHistory();
	
	@Query(value = "SELECT * FROM tbluser WHERE state = 'A'", nativeQuery = true)
	List<User> findAll();
	
}
