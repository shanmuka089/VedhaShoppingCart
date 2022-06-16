package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.UserAlreadyFoundException;
import com.example.demo.models.RolesModel;
import com.example.demo.models.UserModel;
import com.example.demo.repository.UserModelRepository;

@Service
public class UserMOdelService {
	
	@Autowired
	private UserModelRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public UserModel saveUser(UserModel user) {
		if(!userRepo.findByusername(user.getUsername()).isEmpty()) {
			throw new UserAlreadyFoundException("user already exists with these user name");
		}
		if(!userRepo.findByusername(user.getUsername()).isEmpty()) {
			throw new UserAlreadyFoundException("user already exist with this email address");
		}
		Set<RolesModel> updatedRoles=null;
		if(!user.getRoles().isEmpty()) {
			updatedRoles=user.getRoles().stream().map(role->{
			 role.setUser(user);
			 return role;
		}).collect(Collectors.toSet());
		}else {
			RolesModel role=new RolesModel();
			role.setRole("USER");
			role.setUser(user);
			Set<RolesModel> roles=new HashSet<RolesModel>();
			roles.add(role);
			updatedRoles=roles;
		}
		user.setRoles(updatedRoles);
		user.setPassword(encoder.encode(user.getPassword()));;
		return userRepo.save(user);
	}
	
	public UserModel getUserByUserName(String username) {
		return userRepo.findByusername(username).get();
	}
	
	public List<UserModel> getUsers(){
		return userRepo.findAll();
	}

}
