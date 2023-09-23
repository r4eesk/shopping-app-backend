package com.hbox.shopping.hboxshopping.user;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody User user) {
		String userId = userService.registerUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(userId)
				.toUri();
		return ResponseEntity.created(location).build();

	}

	@GetMapping("/checkUsername/{username}")
	public boolean checkUsernameAvailability(@PathVariable String username) {
		return userService.checkUsernameAvailability(username);
	}

	@GetMapping("/{userId}")
	public User getCartByUserId(@PathVariable String userId) {
		User user = userService.getUserById(userId);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");
		return user;
	}
}
