package com.example.demo.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwt.JwtUtil;
import com.example.demo.models.AuthenticateToken;
import com.example.demo.models.ErrorResponse;
import com.example.demo.models.ResponseBack;
import com.example.demo.models.SuccessResponse;
import com.example.demo.models.UserModel;
import com.example.demo.service.UserMOdelService;

@RestController
@RequestMapping("/authService")
@CrossOrigin("http://localhost:4200/")
public class AuthenticationController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserMOdelService service;
	
	@Autowired
	private AuthenticationManager auth;
	
	@PostMapping("/signUp")
	public ResponseEntity<ResponseBack> signUp(@RequestBody UserModel model){
		System.out.println("hello");
		UserModel user=service.saveUser(model);
		System.out.println(user);
		if(user != null) {
			SuccessResponse response=new SuccessResponse();
			response.setMessage("user registered successfully");
			response.setStatus_code(HttpStatus.CREATED.value());
			response.setDate(LocalDate.now());
			return new ResponseEntity<ResponseBack>(response,HttpStatus.CREATED);
		}else {
			ErrorResponse response=new ErrorResponse();
			response.setMessage("error occured while register!...");
			response.setStatus_code(HttpStatus.BAD_REQUEST.value());
			response.setDate(LocalDate.now());
			return new ResponseEntity<ResponseBack>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/signIn")
	public ResponseEntity<ResponseBack> signIn(@RequestBody UserModel model){
		
		System.out.println(model);
		UserModel user=service.getUserByUserName(model.getUsername());
		
		if(user!=null) {
			Authentication authenticator=auth.authenticate(new UsernamePasswordAuthenticationToken(model.getUsername(),model.getPassword()));
			if(authenticator!=null) {
			String token=jwtUtil.generateToken(authenticator);
			User users=(User)authenticator.getPrincipal();
			List<String> roles=users.getAuthorities().stream().map(role->{
				return role.getAuthority();
			}).collect(Collectors.toList());
			
			AuthenticateToken response=new AuthenticateToken();
			response.setToken(token);
			response.setRoles(roles);
			return new ResponseEntity<ResponseBack>(response,HttpStatus.OK);
			}else {
				ErrorResponse response=new ErrorResponse();
				response.setDate(LocalDate.now());
				response.setStatus_code(HttpStatus.BAD_REQUEST.value());
				response.setMessage("invalid credentials");
				return new ResponseEntity<ResponseBack>(response,HttpStatus.BAD_REQUEST);
			}
		}else {
			ErrorResponse response=new ErrorResponse();
			response.setDate(LocalDate.now());
			response.setStatus_code(HttpStatus.BAD_REQUEST.value());
			response.setMessage("user is not registered! please register");
			return new ResponseEntity<ResponseBack>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<UserModel>> getUsers(){
		List<UserModel> users=service.getUsers();
		return new ResponseEntity<List<UserModel>>(users,HttpStatus.OK);
	}

}
