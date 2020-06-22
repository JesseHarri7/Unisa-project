package com.altHealth.activity;

import java.util.List;

import com.altHealth.entity.Client;

public interface ClientActiviy {

	abstract List<Client> findAll();
	abstract String formCreateBtn(Client entity) throws Exception;
}
