package com.trungho.crm.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungho.crm.demo.dao.RoleDao;
import com.trungho.crm.demo.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	@Transactional("securityTransactionManager")
	public List<Role> loadRoles() {
		return roleDao.loadRoles();
	}
	
	@Override
	@Transactional("securityTransactionManager")
	public List<String> getRoleNames() {
		List<String> roleNames = new ArrayList<>();
		List<Role> roles = roleDao.loadRoles();
		for (Role role: roles) {
			roleNames.add(role.getName().substring(5));
		}
		return roleNames;
	}

}
