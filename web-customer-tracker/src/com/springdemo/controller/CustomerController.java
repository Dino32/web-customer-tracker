package com.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springdemo.entity.Customer;
import com.springdemo.service.CustomerService;
import com.springdemo.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model, 
			@RequestParam(required = false) String sort) {
		
		List<Customer> customers = null;
		
		if (sort != null) {
			int sortField = Integer.parseInt(sort);
			customers = customerService.getCustomers(sortField);
		}
		else {
			customers = customerService.getCustomers(SortUtils.LAST_NAME);
		}
		
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		customerService.saveCustomer(customer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id,
									Model model) {
		Customer customer = customerService.getCustomer(id);
		
		model.addAttribute("customer", customer);
		
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id, 
								Model model) {
		customerService.deleteCustomer(id);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("searchName")String searchName, 
									Model model) {
		List<Customer> customers = customerService.searchCustomers(searchName);
		
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
}




