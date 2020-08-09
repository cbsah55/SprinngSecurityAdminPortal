package com.bookstoreadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bookstoreadmin.domains.User;
import com.bookstoreadmin.repository.UserRepository;
import com.bookstoreadmin.service.UserService;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByemail(User user) {
		
		return userRepository.findByemail( user.getEmail());
		
	}

}
