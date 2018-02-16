package it.relatech.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.relatech.model.User;
import it.relatech.services.AuthService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {

	@Autowired
	private AuthService authserv;

	@PostMapping("/login")
	public UserDetails authenticate(@RequestBody User principal) throws Exception {
		return authserv.authenticate(principal);
	}

	@PostMapping("/logout")
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}

}
