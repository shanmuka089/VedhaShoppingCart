package com.example.demo.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateToken implements ResponseBack{
	
	private String token;
	private List<String> roles;

}
