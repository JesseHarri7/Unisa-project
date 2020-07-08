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
	
	@RequestMapping(value = "test")
	public String test() {
		return "/pages/Test.html";
	}

	/*
	 * @RequestMapping(value = "/assetManagement/error") public String Error403() {
	 * return "/pages/403.html"; }
	 */

}
