package com.altHealth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/pages/")
@Controller
public class WebAssetController 
{
	@RequestMapping(value = "client")
    public String client() 
	{
		return "/pages/Client.html";
	}
	
	/*@RequestMapping(value = "/assetManagement/error")
    public String Error403() 
	{
		return "/pages/403.html";
	}*/
	
}
