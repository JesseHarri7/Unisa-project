package com.altHealth.activity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.activity.ClientActiviy;
import com.altHealth.entity.Client;
import com.altHealth.service.ClientService;

@Service
public class ClientActivityService implements ClientActiviy{

	@Autowired
	ClientService service;
	
	@Override
	public String formCreateBtn(Client entity) throws Exception {
		System.out.println("It magically works");
		return "FAIL";
	}

	@Override
	public List<Client> findAll() {
		List<Client> clients = service.readAll();
		System.out.println(clients);
		System.out.println("Got clients info");
		return clients;
	}

	
}
