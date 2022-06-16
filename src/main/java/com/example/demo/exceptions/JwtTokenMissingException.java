package com.example.demo.exceptions;

public class JwtTokenMissingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtTokenMissingException(String desc) {
		super(desc);
	}
}
