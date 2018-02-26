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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.relatech.security.jwt.JWTAuthenticationFilter;
import it.relatech.security.jwt.JWTLoginFilter;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.csrf().disable().httpBasic().and().authorizeRequests()
//				.antMatchers("/login").permitAll()
//				.antMatchers(HttpMethod.GET.POST, "/idea/**").permitAll()
//				.antMatchers(HttpMethod.GET.POST, "/comment/**").permitAll()
//				.antMatchers(HttpMethod.PUT.DELETE, "/idea/**").access("hasRole('ROLE_ADMIN')")
//				.antMatchers(HttpMethod.PUT.DELETE, "/comment/**").access("hasRole('ROLE_ADMIN')")
//				.antMatchers("/user/**").access("hasRole('ROLE_ADMIN')")
//				.antMatchers("/idea/vote/{vote}").permitAll()
//				.antMatchers("/idea/evaluating").access("hasRole('ROLE_ADMIN')")
//				.antMatchers("/idea/accepting/{id}").access("hasRole('ROLE_ADMIN')")
//				.antMatchers("/comment/").access("hasRole('ROLE_ADMIN')")
//				.antMatchers("/comment/accepting/{id}").access("hasRole('ROLE_ADMIN')")
//				.antMatchers("/logout").access("hasRole('ROLE_ADMIN')");
		
		http.csrf().disable().authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers(HttpMethod.GET, "/idea/**").permitAll()
			.antMatchers(HttpMethod.POST, "/idea/**").permitAll()
			.antMatchers(HttpMethod.GET, "/comment/**").permitAll()
			.antMatchers(HttpMethod.POST, "/comment/**").permitAll()
			.antMatchers("/idea/vote/{vote}").permitAll()
			.antMatchers(HttpMethod.PUT, "/idea/**").authenticated()
			.antMatchers(HttpMethod.DELETE, "/idea/**").authenticated()
			.antMatchers(HttpMethod.PUT, "/comment/**").authenticated()
			.antMatchers(HttpMethod.DELETE, "/comment/**").authenticated()
			.antMatchers("/user/**").authenticated()
			.antMatchers("/idea/evaluating").authenticated()
			.antMatchers("/idea/accepting/{id}").authenticated()
			.antMatchers("/comment/").authenticated()
			.antMatchers("/comment/accepting/{id}").authenticated()
			.antMatchers("/logout").authenticated()
	        .and()
	        // We filter the api/login requests
	        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
	                UsernamePasswordAuthenticationFilter.class)
	        // And filter other requests to check the presence of JWT in header
	        .addFilterBefore(new JWTAuthenticationFilter(),
	                UsernamePasswordAuthenticationFilter.class);
		
	}


}
