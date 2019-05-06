package com.trungho.crm.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.trungho.crm.demo.entity.User;
import com.trungho.crm.demo.user.CrmUser;

public interface UserService extends UserDetailsService {
	
	User findByUserName(String userName);
	
	void save(CrmUser crmUser);
	
	void deleteUser(String userName);
}
