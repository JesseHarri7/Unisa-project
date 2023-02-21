package com.altHealth.activity;

import com.altHealth.entity.Client;
import com.altHealth.model.ReturnModel;

public interface ClientActiviy {

	abstract ReturnModel formCreateBtn(Client entity);
	abstract ReturnModel formUpdateBtn(Client entity);
}
