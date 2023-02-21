package com.altHealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.entity.UserRole;
import com.altHealth.repository.UserRoleRepo;
import com.altHealth.service.UserRoleService;


@Service
public  class UserRoleServiceImpl implements UserRoleService 
{

	@Autowired
	private UserRoleRepo repo;

	@Override
	public UserRole create(UserRole entity) {
		UserRole user = repo.findByRole(entity.getRole());
		
		if(user == null) {
			return repo.save(entity);
		}else {
			return null;
		}
		
	}
 
	@Override
	public void delete(UserRole entity) {
		if (entity != null) {
			repo.delete(entity);
		}
	}

	@Override
	public UserRole readById(String id) {
		UserRole user = repo.findByRole(id);
		
		if (user == null) {
			return null;
		}else {	
			return user;
		}
	}

	@Override
	public UserRole update(UserRole entity) {
		UserRole user = repo.findByRole(entity.getRole());
		
		if (user == null) {
			return null;
		}else {
			return repo.save(entity);
		}
	}

	@Override
	public List<UserRole> readAll() {
		return repo.findAll();
	}

	@Override
	public List<UserRole> findAllHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole findByEmail(String email) {
		UserRole user = repo.findByEmail(email);
		
		if(user == null) {
			return null;
		}else {
			return user;
		}
	}

	@Override
	public void updateAll(List<UserRole> entities) {
		repo.saveAll(entities);		
	}

	@Override
	public UserRole findByRole(String role) {
		UserRole user = repo.findByRole(role);
		
		if (user == null) {
			return null;
		}else {	
			return user;
		}
	}

	@Override
	public List<UserRole> findAll() {
		return repo.findAll();
	}
}