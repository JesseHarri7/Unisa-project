package com.altHealth.service;

import java.util.List;

import com.altHealth.entity.Client;


public interface ClientService extends Service<Client, String> {

	List<Client> findClientByName(String name);
	
    List<Client> findClientBySurname(String surname);
    
}
