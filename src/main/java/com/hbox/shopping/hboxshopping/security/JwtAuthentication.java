package com.hbox.shopping.hboxshopping.security;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hbox.shopping.hboxshopping.user.User;
import com.hbox.shopping.hboxshopping.user.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class JwtAuthentication {

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	private String createToken(Authentication authentication) {
		var claims = JwtClaimsSet.builder().issuer("self").issuedAt(Instant.now())
				.expiresAt(Instant.now().plusSeconds(60 * 30)).subject(authentication.getName())
				.claim("roles", createScope(authentication)).build();
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	@PostMapping("/authenticate")
	@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtTokenRequest jwtTokenRequest,
			HttpServletResponse response) {

		var authenticationToken = new UsernamePasswordAuthenticationToken(jwtTokenRequest.username(),
				jwtTokenRequest.password());

		var authentication = authenticationManager.authenticate(authenticationToken);

		var token = createToken(authentication);

		String userId = null;
		User user = userRepository.findById(jwtTokenRequest.username()).orElse(null);
		if (user != null)
			userId = jwtTokenRequest.username();
		else {
			userId = userRepository.findByEmail(jwtTokenRequest.username()).getUserId();
		}

		return ResponseEntity.ok(new JwtResponse(token, userId));
	}

	private String createScope(Authentication authentication) {
		return authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(" "));
	}

}

record JwtResponse(String token, String userId) {
}

record JwtTokenRequest(String username, String password) {
}
