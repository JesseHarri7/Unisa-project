package com.altHealth.activity.service;

import org.springframework.stereotype.Service;

import com.altHealth.activity.ClientActiviy;
import com.altHealth.entity.Client;

@Service
public class ClientActivityService implements ClientActiviy{

	@Override
	public void formCreateBtn(Client entity) {
		System.out.println("It magically works");
	}

	
}
