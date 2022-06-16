package com.example.demo.globalExceptionHandler;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.models.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> invalidCredentials(){
		ErrorResponse response=new ErrorResponse();
		response.setDate(LocalDate.now());
		response.setStatus_code(HttpStatus.BAD_REQUEST.value());
		response.setMessage("invalid credentials!");
		return new ResponseEntity<ErrorResponse>(response,HttpStatus.BAD_REQUEST);
	}

}
