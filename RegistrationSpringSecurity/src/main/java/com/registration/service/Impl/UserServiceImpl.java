package com.registration.service.Impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.registration.domains.User;
import com.registration.domains.UserDto;
import com.registration.domains.security.Role;
import com.registration.domains.security.UserRoles;
import com.registration.repository.UserRepository;
import com.registration.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findByEmail(UserDto userDto) {
		return userRepository.findByEmail(userDto.getEmail());
	}

	@Override
	public boolean userAlreadyExist(UserDto userDto) {
		return (User)userRepository.findByEmail(userDto.getEmail()) != null;
	}

	@Override
	public User registerUser(UserDto userDto) {
		User user = new User();
		
		if(!userAlreadyExist(userDto)) {
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setEmail(userDto.getEmail());
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			Role role = new Role();
			role.setName("ROLE_USER");
			Set<UserRoles> userRoles = new HashSet<>();
			userRoles.add(new UserRoles(user,role));
			
			userRepository.save(user);
			
		}
		return userRepository.findByEmail(user.getEmail());
		
	}

}
