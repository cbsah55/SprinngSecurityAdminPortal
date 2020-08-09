package com.bookstoreadmin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bookstoreadmin.service.UserService;
import com.bookstoreadmin.service.impl.UserDetailsServiceImpl;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
	
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
						
						.antMatchers("/adminportal").permitAll()
						.antMatchers("/login").permitAll()
						.antMatchers("/home").hasAnyAuthority("ROLE_ADMIN")
						.anyRequest().hasAnyAuthority("ROLE_ADMIN")
						
						.and()
						
						.csrf().disable()
						.formLogin()
						.loginPage("/login")
						.failureUrl("/login?error")
						.defaultSuccessUrl("/home")
						.usernameParameter("email")
						.passwordParameter("password")
						
						.and()
						.logout()
						.logoutSuccessUrl("/?logout");
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**","/static/**","/images/**","/css/**","/js/**");
	}
	
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

}
