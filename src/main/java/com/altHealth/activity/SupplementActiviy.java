package com.altHealth.activity;

import com.altHealth.entity.Supplement;
import com.altHealth.model.ReturnModel;

public interface SupplementActiviy {

	abstract ReturnModel formCreateBtn(Supplement entity);
	abstract ReturnModel formUpdateBtn(Supplement entity);
}
