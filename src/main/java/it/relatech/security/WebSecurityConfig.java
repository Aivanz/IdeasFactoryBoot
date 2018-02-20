package it.relatech.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService uds;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder amb) throws Exception {
		amb.userDetailsService(uds);
		amb.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoprov = new DaoAuthenticationProvider();
		daoprov.setUserDetailsService(uds);
		daoprov.setPasswordEncoder(pswEnc());
		return daoprov;
	}

	@Bean
	public PasswordEncoder pswEnc() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@SuppressWarnings({ "static-access" })
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().httpBasic().and().authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers(HttpMethod.GET.POST, "/idea/**").permitAll()
				.antMatchers(HttpMethod.GET.POST, "/comment/**").permitAll()
				.antMatchers(HttpMethod.PUT.DELETE, "/idea/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.PUT.DELETE, "/comment/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/user/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/idea/vote/{vote}").permitAll()
				.antMatchers("/idea/evaluating").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/idea/accepting").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/comment/").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/comment/accepting").access("hasRole('ROLE_ADMIN')")
				.and().logout().permitAll();
	}

}
