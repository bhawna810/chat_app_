package com.example.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authentication.modal.JwtResponse;
import com.example.authentication.modal.LoginModal;
import com.example.authentication.repository.LoginRepository;
import com.example.authentication.services.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class SignUp {
	
	@Autowired
	private LoginRepository loginRepository ;
	
	@Autowired
	private JwtService jwtService;

	@PostMapping("/signUp")
	public ResponseEntity<JwtResponse> signUpMethod(@RequestBody LoginModal user, HttpServletResponse response){
		try {
			LoginModal userdetails = loginRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
			
			if(userdetails != null) {
				return ResponseEntity.ok(new JwtResponse(null , null));
			}
			
			LoginModal savedUser =  loginRepository.save(user);
			
			String jwtToken =jwtService.generateToken(savedUser);
			String jwtResfreshtoken = jwtService.generateResfreshToken(savedUser);
			
			Cookie cookie = new Cookie("jwtResfreshtoken", jwtResfreshtoken);
			cookie.setHttpOnly(true);
			cookie.setSecure(false);
			cookie.setPath("/auth/refresh");
			cookie.setMaxAge(7 * 24 * 60 * 60);
			
			
			
			return ResponseEntity.ok(new JwtResponse(jwtToken, user));
			
			
		}catch(Exception e) {
			return ResponseEntity.ok(new JwtResponse(null , null));
		}
	}
	
	
}
