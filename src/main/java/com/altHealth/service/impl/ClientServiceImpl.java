package com.altHealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.entity.Client;
import com.altHealth.repository.ClientRepo;
import com.altHealth.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepo repo;
	
	@Override
	public Client create(Client entity) {
		Client client = readById(entity.getClientId());
		if(client == null) {
			return repo.save(entity);
		}else {
			return null;
		}
	}

	@Override
	public Client readById(String id) {
		Client client = repo.findClientByClientId(id);
		if (client == null) {
			return null;
		}else {	
			return client;
		}
	}

	@Override
	public List<Client> readAll() {
		return repo.findClientAll();
	}

	@Override
	public Client update(Client entity) {
		Client client = repo.findClientByClientId(entity.getClientId());
		if (client == null) {
			return null;
		}else {
			return repo.save(entity);
		}
	}
	
	@Override
	public void updateAll(List<Client> entities) {
		repo.saveAll(entities);
	}

	@Override
	public void delete(Client entity) {
		if (entity != null) {
			repo.delete(entity);
		}
	}

	@Override
	public List<Client> findAllHistory() {
		return repo.findClientAllHistory();
	}

	@Override
	public List<Client> findClientByName(String name) {
		List<Client> clients = repo.findClientByCName(name);
		return returnList(clients);
	}

	@Override
	public List<Client> findClientBySurname(String surname) {
		List<Client> clients = repo.findClientByCSurname(surname);
		return returnList(clients);
	}
	
	private <E> List<E> returnList(List<E> list){
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
	}

}
