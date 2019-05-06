package com.trungho.crm.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
//		return "plain-login";
		
		return "login/login-form";
	}
	
	@GetMapping("/leaders")
	public String showLeaders() {
		return "login/leaders";
	}
	
	@GetMapping("/admin")
	public String showAdmin() {
		return "login/admin";
	}
	
	// show this page when user is not authorized to access the resource
	@GetMapping("/access-denied")
	public String showAccessDeniedPage() {
		return "error/access-denied";
	}
}
