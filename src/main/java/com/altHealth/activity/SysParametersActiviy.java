package com.altHealth.activity;

import com.altHealth.entity.SysParameters;
import com.altHealth.model.ReturnModel;

public interface SysParametersActiviy {

	abstract ReturnModel formUpdateBtn(SysParameters entity);
	abstract ReturnModel formUpdateVatBtn();
}
