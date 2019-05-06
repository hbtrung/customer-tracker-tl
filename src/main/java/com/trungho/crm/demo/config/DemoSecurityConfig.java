package com.trungho.crm.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trungho.crm.demo.service.UserService;

@Configuration
//@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/customer/showForm*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/customer/save*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/customer/delete").hasRole("ADMIN")
			.antMatchers("/customer").hasRole("EMPLOYEE")
			.antMatchers("/leaders").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers("/api/customers/**").permitAll()
			.antMatchers("/actuator/**").hasRole("ADMIN")
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/").hasAnyRole("EMPLOYEE")
			.and()
			.formLogin()
			.loginPage("/showMyLoginPage")
			.loginProcessingUrl("/authenticateTheUser")
			.successHandler(customAuthenticationSuccessHandler)
			.permitAll()
			.and().logout().permitAll()
			.and().exceptionHandling().accessDeniedPage("/access-denied")
			.and().csrf().disable();
		
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
}
