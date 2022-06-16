package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.exceptions.JwtTokenMissingException;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.AuthenticationService;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private AuthenticationService service;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header=request.getHeader("Authorization");
		if(header==null || !header.startsWith("HTTP_Token")) {
			throw new JwtTokenMissingException("jwt token missing in header!..");
		}
		String token=header.substring("HTTP_TOKEN".length()+1);
		jwtUtil.validateToken(token);
		String username=jwtUtil.getUserName(token);
		
		UserDetails user=service.loadUserByUsername(username);
		if(SecurityContextHolder.getContext().getAuthentication()==null) {
			UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
	}

}
