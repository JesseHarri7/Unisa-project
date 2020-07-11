package com.altHealth.controller.frontendController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping(value = "/pages/")
@Controller
public class WebAssetController {
	@RequestMapping(value = "client")
	public String client() {
		return "/pages/Client.html";
	}

	@RequestMapping(value = "supplier")
	public String supplier() {
		return "/pages/Supplier.html";
	}

	@RequestMapping(value = "supplement")
	public String supplement() {
		return "/pages/Supplement.html";
	}

	@RequestMapping(value = "settings")
	public String settings() {
		return "/pages/Settings.html";
	}

	@RequestMapping(value = "cart")
	public String cart() {
		return "/pages/Cart.html";
	}
	
	@RequestMapping(value = "invoice")
	public String invoice() {
		return "/pages/Invoice.html";
	}
	
	@RequestMapping(value = "home")
	public String Dashboard() {
		return "/pages/Dashboard.html";
	}
	
	@RequestMapping(value = "unpaidInvoices")
	public String UnpaidInvoices() {
		return "/pages/reports/Unpaid-Invoices.html";
	}
	
	@RequestMapping(value = "birthdaysForToday")
	public String BirthdaysForToday() {
		return "/pages/reports/BirthdaysForToday.html";
	}
	
	@RequestMapping(value = "minStockLevels")
	public String MinStockLevels() {
		return "/pages/reports/MinStockLevels.html";
	}
	
	@RequestMapping(value = "top10")
	public String Top10() {
		return "/pages/reports/Top10.html";
	}
	
	@RequestMapping(value = "purchasesStats")
	public String PurchasesStats() {
		return "/pages/reports/PurchasesStats.html";
	}
	
	@RequestMapping(value = "clientInfoQuery")
	public String ClientInfoQuery() {
		return "/pages/reports/ClientInfoQuery.html";
	}
	
	@RequestMapping(value = "test")
	public String test() {
		return "/pages/Test.html";
	}
	
	

	/*
	 * @RequestMapping(value = "/assetManagement/error") public String Error403() {
	 * return "/pages/403.html"; }
	 */

}
