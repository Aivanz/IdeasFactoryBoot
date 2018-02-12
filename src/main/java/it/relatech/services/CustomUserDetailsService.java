package it.relatech.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.relatech.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService us;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = us.getByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("Username not found");

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
				true, true, true, getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("" + user.getUsprof()));
		return authorities;
	}

}
