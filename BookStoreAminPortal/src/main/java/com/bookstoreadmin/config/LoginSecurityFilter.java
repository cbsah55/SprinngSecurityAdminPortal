package com.bookstoreadmin.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class LoginSecurityFilter extends GenericFilterBean{
	
	//this filter is used to prevent authenticated users from returning to login page 

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			if(SecurityContextHolder.getContext().getAuthentication() != null
					&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
					&& ((HttpServletRequest) request).getRequestURI().equals("/adminportal/login")) {
				System.out.println("User is already authenticated and is trying to request the login path again.logout first");
				((HttpServletResponse) response).sendRedirect("/adminportal/home");
			}
			chain.doFilter(request, response);
	}



}
