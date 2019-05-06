package com.trungho.crm.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.trungho.crm.demo.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	@Qualifier(value = "securitySessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public Role findRoleByName(String roleName) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Role> query = session.createQuery("from Role where name=:roleName", Role.class);
		query.setParameter("roleName", roleName);
		
		Role role = null;
		
		try {
			role = query.getSingleResult();
		} catch (Exception e) {
			role = null;
		}
		
		return role;
	}
	
	@Override
	public List<Role> loadRoles() {
		Session session = sessionFactory.getCurrentSession();
		Query<Role> query = session.createQuery("from Role", Role.class);
		return query.getResultList();
	}

}
