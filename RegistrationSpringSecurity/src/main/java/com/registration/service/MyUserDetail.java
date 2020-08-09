package com.registration.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.registration.domains.User;
import com.registration.domains.security.UserRoles;

public class MyUserDetail implements UserDetails {
	private User user;

	public MyUserDetail(User user) {
		this.user = user;
	}
	 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<UserRoles> userRoles = user.getUserRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for(UserRoles ur : userRoles) {
			authorities.add(new SimpleGrantedAuthority(ur.getRole().getName()));
		}
		return authorities;
		
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
