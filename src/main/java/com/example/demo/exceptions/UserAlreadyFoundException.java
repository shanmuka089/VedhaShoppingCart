package com.example.demo.exceptions;

public class UserAlreadyFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserAlreadyFoundException(String desc) {
		super(desc);
	}
}
