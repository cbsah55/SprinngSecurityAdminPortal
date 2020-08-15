package com.bookstoreadmin.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminHomePageController {
	


	
	@RequestMapping("/login")
	public String login() {
		System.out.println("login"+SecurityContextHolder.getContext().getAuthentication());
		
		return "loginPage";
	}

	@RequestMapping("/home")
	public String home() {
		System.out.println("home"+SecurityContextHolder.getContext().getAuthentication());
		return "adminPage";
	}
}
