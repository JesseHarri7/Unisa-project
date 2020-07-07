package com.altHealth.model;

import java.util.List;

import com.altHealth.entity.Client;
import com.altHealth.entity.Supplement;

public interface CartModelActivity {
	abstract void setClient(Client client);
	abstract Client getClient();
	abstract void setSupplement(Supplement supplement);
	abstract void setSupplementList(List<Supplement> supplementList);
	abstract List<Supplement> getSupplementList();
}
