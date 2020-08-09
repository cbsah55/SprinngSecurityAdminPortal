package com.bookstoreadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bookstoreadmin.domains.MyUserDetails;
import com.bookstoreadmin.domains.User;
import com.bookstoreadmin.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByemail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("User doesn't exists!");
		}
		return new MyUserDetails(user);
	}

}
