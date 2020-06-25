package com.altHealth.activity;

import java.util.List;

import com.altHealth.entity.Client;
import com.altHealth.entity.ReturnModel;

public interface ClientActiviy {

	abstract List<Client> findAll();
	abstract ReturnModel formCreateBtn(Client entity);
}
