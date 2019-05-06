package com.trungho.crm.demo.dao;

import java.util.List;

import com.trungho.crm.demo.entity.Role;

public interface RoleDao {

	Role findRoleByName(String roleName);
	
	List<Role> loadRoles();
}
