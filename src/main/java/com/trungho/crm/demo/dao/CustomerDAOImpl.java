package com.trungho.crm.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.trungho.crm.demo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	@Qualifier(value = "customerSessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
//		Query<Customer> query = currentSession.createQuery("from Customer order by lastName", Customer.class);
//		
//		List<Customer> customers = query.getResultList();
		
		return currentSession.createQuery("from Customer order by lastName", Customer.class).getResultList();
	}

	@Override
	public void saveCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
//		Customer customer = session.get(Customer.class, id);
		return session.get(Customer.class, id);
	}
	
	@Override
	public void deleteCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from Customer where id =: customerId");
		query.setParameter("customerId", id);
		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query<Customer> query = null;
		
		if (name != null && name.trim().length() > 0) {
			query = session.createQuery("from Customer where lower(firstName) like :searchName "
					+ "or lower(lastName) like :searchName order by lastName", Customer.class);
			query.setParameter("searchName", "%" + name.toLowerCase() + "%");
		} else {
			query = session.createQuery("from Customer", Customer.class);
		}
		
		return query.getResultList();
	}
}
