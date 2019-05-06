package com.trungho.crm.demo.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.trungho.crm.demo.entity.Customer;
import com.trungho.crm.demo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	// trim String to null
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	// list all the customers
	@GetMapping("/list")
	public String listCustomers(Model model) {
		List<Customer> customers = customerService.getCustomers();
		
		model.addAttribute("customers", customers);
		
		return "customers/customer-list";
	}
	
	// show the form for adding a new customer
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		model.addAttribute("customer", new Customer());
		return "customers/customer-form";
	}
	
	// show the form for updating a current customer
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int customerId, Model model) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customer", customer);
		return "customers/customer-form";
	}
	
	// add/update customer
	@PostMapping("/saveCustomer")
	public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "customers/customer-form";
		customerService.saveCustomer(customer);
		return "redirect:/customer/list";
	}
	
	// delete a customer
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int customerId) {
		customerService.deleteCustomer(customerId);
		return "redirect:/customer/list";
	}
	
	// search customers based on substring
	@GetMapping("/search")
	public String searchCustomer(@RequestParam("searchName") String custName, Model model) {
		model.addAttribute("customers", customerService.searchCustomers(custName));
		return "customers/customer-list";
	}
	
}
