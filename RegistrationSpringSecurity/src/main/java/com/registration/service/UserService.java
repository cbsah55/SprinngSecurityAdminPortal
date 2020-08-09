package com.registration.service;

import com.registration.domains.User;
import com.registration.domains.UserDto;

public interface UserService {
	
	User findByEmail(UserDto userDto);
	
	boolean userAlreadyExist(UserDto userDto);
	
	User registerUser(UserDto userDto);

}
