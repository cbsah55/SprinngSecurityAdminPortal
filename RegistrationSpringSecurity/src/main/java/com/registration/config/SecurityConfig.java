package com.registration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.registration.service.UserService;
import com.registration.service.Impl.UserDetailsServiceImpl;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService());
		return authProvider;
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		   http
		   		.authorizeRequests()
		   		.antMatchers("/login").permitAll()
		   		.antMatchers("/index").permitAll()
		   		.antMatchers("/register").permitAll()
		   		.antMatchers("/myprofile").authenticated()
		   		.anyRequest().authenticated()
		   		
		   		.and()
		   		
		   		.csrf().disable()
		   		.formLogin()
		   		.loginPage("/login")
		   		.failureUrl("/login?success=false")
		   		.defaultSuccessUrl("/myprofile")
		   		.usernameParameter("email")
		   		.passwordParameter("password")
		   		
		   		.and()
		   		.logout()
		   		.logoutSuccessUrl("/logout?success=true");
	}
	
	
	

}
