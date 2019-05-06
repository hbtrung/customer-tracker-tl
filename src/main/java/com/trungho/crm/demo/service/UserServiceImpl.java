package com.trungho.crm.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungho.crm.demo.dao.RoleDao;
import com.trungho.crm.demo.dao.UserDao;
import com.trungho.crm.demo.entity.Role;
import com.trungho.crm.demo.entity.User;
import com.trungho.crm.demo.user.CrmUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;	

	@Override
	@Transactional("securityTransactionManager")
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional("securityTransactionManager")
	public void save(CrmUser crmUser) {
		User user = new User();
		user.setUserName(crmUser.getUserName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());
		
		Role userRole = roleDao.findRoleByName("ROLE_" + crmUser.getFormRole());
		if (!(userRole.getName().equals("ROLE_EMPLOYEE"))) {
			user.addRole(roleDao.findRoleByName("ROLE_EMPLOYEE"));
		}
		
		user.addRole(userRole);
		
		userDao.save(user);
	}
	
	@Override
	@Transactional("securityTransactionManager")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), 
				mapRolesToAuthorities(user.getRoles()));		
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional("securityTransactionManager")
	public void deleteUser(String userName) {
		userDao.deleteUser(userName);
	}
}
