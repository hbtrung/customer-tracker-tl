package com.trungho.crm.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.trungho.crm.demo.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	@Qualifier(value = "securitySessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public User findByUserName(String userName) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<User> query = session.createQuery("from User where userName=:uName", User.class);
		query.setParameter("uName", userName);
		
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			user = null;
		}
		
		return user;
	}

	@Override
	public void save(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
	}
	
	@Override
	public void deleteUser(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Query<User> query = session.createQuery("delete from User where userName=:uName", User.class);
		query.setParameter("uName", userName);
		query.executeUpdate();
	}
}
