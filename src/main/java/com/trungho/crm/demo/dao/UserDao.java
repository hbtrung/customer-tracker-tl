package com.trungho.crm.demo.dao;

import com.trungho.crm.demo.entity.User;

public interface UserDao {
	
	User findByUserName(String userName);
	
	void save(User user);
	
	void deleteUser(String userName);
}
