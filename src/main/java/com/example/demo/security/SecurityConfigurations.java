package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.service.AuthenticationService;

@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationService service;
	
	@Autowired
	private DaoAuthenticationProvider provider;

	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/authService/*");
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvide=new DaoAuthenticationProvider();
		authProvide.setPasswordEncoder(passwordEncoder());
		authProvide.setUserDetailsService(service);
		return authProvide;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("making authorities enabling");
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/authService/signIn","/authService/signUp").permitAll()
		.and().authorizeRequests()	
		.anyRequest().authenticated()
		.and().httpBasic();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

}
