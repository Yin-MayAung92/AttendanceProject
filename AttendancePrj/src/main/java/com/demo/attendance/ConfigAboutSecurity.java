package com.demo.attendance;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.demo.attendance.service.EmployeeInfoService;

@EnableWebSecurity
public class ConfigAboutSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private EmployeeInfoService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().antMatchers("/", "/index", "/resources/**").permitAll()
		.antMatchers("/admin/**").hasRole("Role_Admin")
		.antMatchers("/employee/**").hasRole("Role_Employee")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin().loginPage("/index")
		.loginProcessingUrl("/login")
		.usernameParameter("mail")
		.passwordParameter("password")
		.defaultSuccessUrl("/home", true).and().logout().logoutUrl("/logout")
				.logoutSuccessUrl("/").invalidateHttpSession(true);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}
}
