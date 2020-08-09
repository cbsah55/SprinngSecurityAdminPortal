package com.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.registration.domains.User;
import com.registration.domains.UserDto;
import com.registration.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping ("/")
	public String index() {
		return "index";
		
	}
	
	@RequestMapping ("/myprofile")
	public ModelAndView myProfile() {
		ModelAndView mav =  new ModelAndView();
		
		mav.setViewName("myprofile");
		return mav;
	}
	
	@RequestMapping ("/login")
	public String login ()	{
		return "login";
	}
	
	@RequestMapping("/login?success=false")
	public String loginFailure() {
		
		return "login";
	}
	
	@RequestMapping ("/registration")
	public String register(Model model) {
		UserDto userDto = new UserDto();
		
		model.addAttribute("user",userDto);
		
		return "register";
	}
	
	@RequestMapping (value = "/registration",method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("user") UserDto userDto,ModelMap modelMap) {
		ModelAndView mav = new ModelAndView();
		User user = userService.findByEmail(userDto);
		
		if(userService.userAlreadyExist(userDto)) {
			mav.addObject("successMessage", "User with email/username "+userDto.getEmail()+" already exist. Go to login");
			
		}else {
			userService.registerUser(userDto);
			mav.addObject("successMessage", "You are registered successfully. Go to login!");
		}
		
		
		
		
		mav.setViewName("register");
		return mav;
	}

}
