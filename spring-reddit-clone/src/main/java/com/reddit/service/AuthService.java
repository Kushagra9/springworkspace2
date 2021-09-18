package com.reddit.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.dto.AuthenticationResponse;
import com.reddit.dto.LoginRequest;
import com.reddit.dto.RefreshTokenRequest;
import com.reddit.dto.RegisterRequest;
import com.reddit.exception.SpringRedditException;
import com.reddit.model.NotificationEmail;
import com.reddit.model.User;
import com.reddit.model.VerificationToken;
import com.reddit.repository.UserRepository;
import com.reddit.repository.VerificationTokenRepository;
import com.reddit.security.JwtProvider;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class AuthService {
	//@Autowired
	private final PasswordEncoder passwordEncoder;
	//@Autowired
	private final UserRepository userRepository;
	
	private final VerificationTokenRepository verificationTokenRepository;
	
	private final AuthenticationManager authenticationManager;
	private final MailService mailService;
	
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user=new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);
		String token=generateVerificationToken(user);
		
		mailService.sendEmail(new NotificationEmail("Please Activate Your Account", user.getEmail(), "Thank you for signing up to Spring Reddit,"+
		"Please click on URL to activate your account :"+
		"http://localhost:8080/api/auth/accountVerification/"+token		));
	}
	private String generateVerificationToken(User user) {
		// TODO Auto-generated method stub
		String token=UUID.randomUUID().toString();
		VerificationToken verificationToken=new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(()->new SpringRedditException("Invalid Token!"));
		fetchUserAndEnable(verificationToken.get());
		
	
	}
	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(()-> new SpringRedditException("User not found with "+username));
		user.setEnabled(true);
		userRepository.save(user);
	}
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	//	if(authenticate.isAuthenticated())
	    	SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenService.generateRefreshToken().getToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
				.username(loginRequest.getUsername())
				.build();
		
	}
	  @Transactional(readOnly = true)
	    public User getCurrentUser() {
	        Authentication principal =  SecurityContextHolder. getContext().getAuthentication();
	        return userRepository.findByUsername(principal.getName())
	                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getName()));
	    }
	public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
		// TODO Auto-generated method stub
		 refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		 String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
		 return AuthenticationResponse.builder()
				 .authenticationToken(token)
				 .refreshToken(refreshTokenRequest.getRefreshToken())
				 .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
				 .username(refreshTokenRequest.getUsername())
				 .build();
	}
}
