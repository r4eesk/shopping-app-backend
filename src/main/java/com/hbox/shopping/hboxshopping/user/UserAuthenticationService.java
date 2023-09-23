package com.hbox.shopping.hboxshopping.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password = null;
		String userId = null;
		List<GrantedAuthority> roles = null;
		User user = userRepository.findById(username).orElse(null);

		if (user == null) {
			user = userRepository.findByEmail(username);

		}
		if (user == null) {
			throw new UsernameNotFoundException("No user exists with username " + username);
		}

		password = user.getPassword();
		userId = user.getUserId();
		roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

		return new org.springframework.security.core.userdetails.User(userId, password, roles);
	}

}
