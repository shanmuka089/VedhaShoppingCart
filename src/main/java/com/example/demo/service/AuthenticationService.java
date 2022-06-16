package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.models.UserModel;

@Service
public class AuthenticationService implements UserDetailsService{

	@Autowired
	private UserMOdelService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserModel user = service.getUserByUserName(username);
		System.out.println(user);
		if(user==null) {
			throw new UsernameNotFoundException("user not found with these username");
		}
		List<SimpleGrantedAuthority> roles=user.getRoles().stream().map(role -> {
			return new SimpleGrantedAuthority(role.getRole());
		}).toList();
		return User.withUsername(user.getUsername()).password(user.getPassword())
				.authorities(roles).build();
	}

}
