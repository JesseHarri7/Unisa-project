package com.altHealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.altHealth.entity.User;
import com.altHealth.repository.UserRepo;
import com.altHealth.service.UserService;

@Service
public  class UserServiceImpl implements UserService 
{

	@Autowired
	private UserRepo repo;
	
	/*@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;*/
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
	
	@Override
	public List<User> findByFirstName(String name) {
		List<User> users = repo.findByFirstName(name);
		
		return returnList(users);
	}
	
	@Override
	public List<User> findByLastName(String name) {
		List<User> users = repo.findByLastName(name);
		
		return returnList(users);
	}
	
	@Override
	public User findByEmail(String email) {
		User user=  repo.findByEmail(email);
		
		if (email != null) {
			return user;
		}else {
			return null;	
		}
	}

	@Override
	public User create(User entity) {
		User user = repo.findByEmail(entity.getEmail());
		
		if(user == null) {
			entity.setPassword(passwordEncoder().encode(entity.getPassword()));
			return repo.save(entity);
		}else {
			return null;
		}
		
	}
	
	public List<User> readAll() {
		return repo.findAll();
	}
 
	@Override
	public void delete(User entity) {
		if (entity != null) {
			repo.delete(entity);
		}
	}

	@Override
	public User readById(String id) {
		User user = repo.findByEmail(id);
		
		if (user == null) {
			return null;
		}else {	
			return user;
		}
	}

	@Override
	public User update(User entity) {
		User user = repo.findByEmail(entity.getEmail());
		
		if (user == null) {
			return null;
		}else {
			return repo.save(entity);
		}
	}
	
	@Override
	public void updateAll(List<User> entities) {
		repo.saveAll(entities);		
	}

	@Override
	public List<User> findAllHistory() {
		return repo.findAllHistory();
	}
	
	private <E> List<E> returnList(List<E> list){
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
	}
}