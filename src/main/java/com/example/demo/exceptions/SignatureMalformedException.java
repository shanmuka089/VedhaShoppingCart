package com.example.demo.exceptions;

public class SignatureMalformedException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SignatureMalformedException(String desc) {
		super(desc);
	}

}
