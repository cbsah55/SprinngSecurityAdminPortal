package com.bookstoreadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminHomePageController {
	
	
	
	@RequestMapping("/adminportal")
	public String admin() {
		return "redirect:/login";
	}

	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	//redirecting to home so it again redirects to login
	@RequestMapping("/logout")
	public String logout() {
		return "home";
	}
	
	
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
}
