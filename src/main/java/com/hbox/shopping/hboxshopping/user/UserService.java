package com.hbox.shopping.hboxshopping.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String registerUser(User user) {
		User testUser = userRepository.findByEmail(user.getEmail());
		User testUser2 = userRepository.findById(user.getUserId()).orElse(null);
		if (testUser != null || testUser2 != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username/Email already exists!");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		return user.getUserId();
	}

	public boolean checkUsernameAvailability(String username) {
		return userRepository.findById(username).orElse(null) == null && username.length() >= 6;
	}

	public User getUserById(String userId) {
		return userRepository.findById(userId).orElse(null);
	}
}
