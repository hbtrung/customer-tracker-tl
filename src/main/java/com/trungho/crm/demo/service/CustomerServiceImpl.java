package com.trungho.crm.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungho.crm.demo.dao.CustomerDAO;
import com.trungho.crm.demo.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;
	
	@Override
	@Transactional("customerTransactionManager")
	public List<Customer> getCustomers() {
		return customerDAO.getCustomers();
	}

	@Override
	@Transactional("customerTransactionManager")
	public void saveCustomer(Customer customer) {
		customerDAO.saveCustomer(customer);
	}

	@Override
	@Transactional("customerTransactionManager")
	public Customer getCustomer(int id) {
		return customerDAO.getCustomer(id);
	}
	
	@Override
	@Transactional("customerTransactionManager")
	public void deleteCustomer(int id) {
		customerDAO.deleteCustomer(id);
	}

	@Override
	@Transactional("customerTransactionManager")
	public List<Customer> searchCustomers(String name) {
		return customerDAO.searchCustomers(name);
	}
}
