package com.trungho.crm.demo.service;

import java.util.List;

import com.trungho.crm.demo.entity.Role;

public interface RoleService {

	List<Role> loadRoles();
	
	List<String> getRoleNames();
}
