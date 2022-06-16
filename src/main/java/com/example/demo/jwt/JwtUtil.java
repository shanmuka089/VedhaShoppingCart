package com.example.demo.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.example.demo.exceptions.SignatureMalformedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {

	@Value("${jwt.secretKey}")
	private String secret;
	
	@Value("${jwt.tokenValidity}")
	private long validityTime;
	public String generateToken(Authentication authentication) {
		User user=(User)authentication.getPrincipal();
		Claims claims=Jwts.claims().setSubject(user.getUsername());
		long currentTime=System.currentTimeMillis();
		long experyTime=currentTime+validityTime;
		Date currentDate=new Date(currentTime);
		Date expiryDate=new Date(experyTime);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(currentDate)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS256,secret)
				.compact();
	}
	
	public void validateToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		}catch(SignatureException ex) {
			throw new SignatureMalformedException("invalid jwt token!...");
		}catch(ExpiredJwtException ex) {
			throw new SignatureMalformedException("jwt token is expired!...");
		}catch(IllegalArgumentException ex) {
			throw new SignatureMalformedException("invalid arguments!...");
		}	
	}
	
	public String getUserName(String token) {
		try {
			Claims body=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			String username=body.getSubject();
			return username;
		}catch(Exception e) {
			e.getMessage();
		}
		return null;
	}
}
