package com.altHealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altHealth.entity.Client;
import com.altHealth.service.ClientService;

@RestController
@RequestMapping("/client/")
public class ClientController implements Controller<Client, String>{

	@Autowired
	ClientService service;

	@Override
	public Client findById(String id) {
		return service.readById(id);
	}

	@Override
	public Client create(Client entity) {
		return service.create(entity);
	}

	@Override
	public void update(Client entity) {
		service.update(entity);
	}

	@Override
	//Find All
	@RequestMapping(value = "findAll", method = RequestMethod.GET)
	public List<Client> findAll() {
		return service.readAll();
	}

	@Override
	public List<Client> findAllHistory() {
		return service.findAllHistory();
	}

	@Override
	public void delete(String id) {
		Client entity = service.readById(id);
		if (entity != null) {
			service.delete(entity);
		}
	}
	
	//findByName
	@RequestMapping(value = "findClientByName/{name}", method = RequestMethod.GET)
	public List<Client> findClientByName(@PathVariable String name) {
		return service.findClientByName(name);
	}

	//findBySurname
	@RequestMapping(value = "findClientBySurname/{surname}", method = RequestMethod.GET)
	public List<Client> findClientBySurname(@PathVariable String surname) {
		return service.findClientBySurname(surname);
	}	
	
}
