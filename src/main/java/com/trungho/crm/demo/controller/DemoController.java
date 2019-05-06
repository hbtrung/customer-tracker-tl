package com.trungho.crm.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

	@GetMapping("/")
	public String showMainMenu() {
//		return "hello";
		return "redirect:/customer/list";
	}
	
	@GetMapping("/home")
	public String showHome() {
		return "home";
	}
}
