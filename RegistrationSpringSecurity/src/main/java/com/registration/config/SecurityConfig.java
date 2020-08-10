package com.registration.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.web.filter.GenericFilterBean;

import com.registration.service.Impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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

	final static String[] PUBLIC_MATCHERS = { "/css/**", "/static/**", "/images/**", "/js/**" };

	// this filters the authenticated users to accecss back the login page
	class LoginPageFilter extends GenericFilterBean {

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			if (SecurityContextHolder.getContext().getAuthentication() != null
					&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
					&&( ((HttpServletRequest) request).getRequestURI().equals("/login")
					|| ((HttpServletRequest) request).getRequestURI().equals("/registration")))	{
				System.out.println("user is authenticated but trying to access login page, redirecting to /");
				((HttpServletResponse) response).sendRedirect("/myprofile");
			}
			chain.doFilter(request, response);

		}

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * After, we need to add the filter to the existing filter chain, but we can't
		 * add it anywhere. It has to be after the authentication has been resolved by
		 * the session id (otherwise,
		 * SecurityContextHolder.getContext().getAuthentication() would always return
		 * null) and it has to be before the existing filter that generates the login
		 * page.
		 * 
		 * The best candidate is before UsernamePasswordAuthenticationFilter: due to the
		 * fact that we're checking if the user is authenticated,
		 * (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()),
		 * we can safely assume that the user does not need to be processed in
		 * UsernamePasswordAuthenticationFilter (since he's already logged in!).
		 * 
		 * To add the custom filter at that position, we need to add this at the top of
		 * our configure(HttpSecurity http) method:
		 * 
		 */
		http.addFilterBefore(new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);

		http

				.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().antMatchers("/login").permitAll()
				.antMatchers("/").permitAll().antMatchers("/index").permitAll().antMatchers("/registration").permitAll()
				.antMatchers("/myprofile").authenticated().anyRequest().authenticated()

				.and()

				.csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error")
				.defaultSuccessUrl("/myprofile").usernameParameter("email").passwordParameter("password")

				.and().logout().logoutSuccessUrl("/?logout=success");
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

}
