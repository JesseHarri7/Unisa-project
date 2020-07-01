package com.altHealth.activity;

import com.altHealth.entity.ReturnModel;
import com.altHealth.entity.SysParameters;

public interface SysParametersActiviy {

	abstract ReturnModel formUpdateBtn(SysParameters entity);
	abstract ReturnModel formUpdateVatBtn();
}
