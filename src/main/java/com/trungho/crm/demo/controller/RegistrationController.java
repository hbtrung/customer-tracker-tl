package com.trungho.crm.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trungho.crm.demo.entity.Role;
import com.trungho.crm.demo.entity.User;
import com.trungho.crm.demo.service.RoleService;
import com.trungho.crm.demo.service.UserService;
import com.trungho.crm.demo.user.CrmUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	private List<String> roles;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// load the roles to choose when register a new user
	@PostConstruct
	protected void loadRoles() {
		roles = roleService.getRoleNames();
	}
	
	// trim String to null
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	// show the form to register a new user
	@GetMapping("/showRegistrationForm")
	public String showRegistrationForm(Model model) {
		model.addAttribute("crmUser", new CrmUser());
		model.addAttribute("roles", roles);
		return "register/registration-form";
	}
	
	// process the registration if the form doesn't contain error and userName doesn't exist in database
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser crmUser, BindingResult bindingResult, 
			Model model) {
		String userName = crmUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("roles", roles);
			return "register/registration-form";
		}
		
		User existing = userService.findByUserName(userName);
		if (existing != null) {
			model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("roles", roles);
			model.addAttribute("registrationError", "username already exists.");
			return "register/registration-form";
		}
		
		userService.save(crmUser);
		
		logger.info("Successfully created user: " + userName);
		return "register/registration-confirmation";
	}
}
