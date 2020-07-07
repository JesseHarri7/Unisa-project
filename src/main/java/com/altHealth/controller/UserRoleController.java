package com.altHealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.entity.UserRole;
import com.altHealth.service.UserRoleService;

@RestController
@RequestMapping("/userRole/")
public class UserRoleController {
	@Autowired
	UserRoleService service;

	// create
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserRole create(@RequestBody UserRole userRole) {
		return service.create(userRole);
	}

	// update
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody UserRole userRole) {
		service.update(userRole);
	}

	// find All
	@RequestMapping(value = "findAll", method = RequestMethod.GET)
	public List<UserRole> findAll() {
		return service.readAll();
	}

	// Find All History
	@RequestMapping(value = "findAllHistory", method = RequestMethod.GET)
	public List<UserRole> findAllHistory() {
		return service.findAllHistory();
	}

	// delete user
	@RequestMapping(value = "deleteUser/{email}", method = { RequestMethod.GET, RequestMethod.DELETE })
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable("email") String email) {
		UserRole userRole = service.findByEmail(email);

		if (userRole != null) {
			service.delete(userRole);
		}
	}
	
	// delete role
	@RequestMapping(value = "deleteRole/{role}", method = { RequestMethod.GET, RequestMethod.DELETE })
	@ResponseStatus(HttpStatus.OK)
	public void deleteRole(@PathVariable("role") String role) {
		UserRole userRole = service.findByRole(role);

		if (userRole != null) {
			service.delete(userRole);
		}
	}

	// Find by email
	@RequestMapping(value = "findByEmail/{email}", method = RequestMethod.GET)
	public UserRole findByEmail(@PathVariable String email) {
		return service.findByEmail(email);
	}

}