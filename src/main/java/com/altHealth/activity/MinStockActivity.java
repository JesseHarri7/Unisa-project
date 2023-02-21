package com.altHealth.activity;

import java.util.List;

import com.altHealth.entity.VO.ReportVO;
import com.altHealth.model.ReturnModel;

public interface MinStockActivity {

	abstract ReturnModel requestStock(List<ReportVO> reportVO);
}
