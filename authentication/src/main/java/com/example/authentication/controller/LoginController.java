package com.example.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

//Lombok import
import lombok.RequiredArgsConstructor;

import com.example.authentication.repository.LoginRepository;
import com.example.authentication.modal.JwtResponse;
import com.example.authentication.modal.LoginModal;
import com.example.authentication.services.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	private LoginRepository loginRepository ;

	@Autowired
	private JwtService jwtService;

	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> loginMethod(@RequestBody LoginModal user, HttpServletResponse response) {
		
		try {
		   System.out.println("Received Request: " + user.getUsername());  
		   System.out.println("Received Request: " + user.getPassword());  
			
//		   ResponseEntity<JwtResponse>  res = AuthService.getValueandCheckValue(user.getUsername(), user.getPassword());
		   
		   try {
			    LoginModal userdetails = loginRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
				if(userdetails == null) {
					 return ResponseEntity.ok(new JwtResponse(null, null));
				}
				
				String jwttoken = jwtService.generateToken(userdetails);
				String jwtResfreshtoken = jwtService.generateResfreshToken(userdetails);
				
				
				LoginModal userValue = loginRepository.findByUsername(user.getUsername());
				
				System.out.println("jwttoken "+ jwttoken);
				
				System.out.println("jwtResfreshtoken" + jwtResfreshtoken);
				
				
				Cookie cookie = new Cookie("jwtResfreshtoken", jwtResfreshtoken);
				cookie.setHttpOnly(true);
				cookie.setSecure(false);
				cookie.setPath("/auth/refresh");
				cookie.setMaxAge(7 * 24 * 60 * 60);

				response.addCookie(cookie);
				
				 return ResponseEntity.ok(new JwtResponse(jwttoken, userValue));
			}
			catch(Exception e) {
				e.printStackTrace();
				 return ResponseEntity.ok(new JwtResponse(null, null));
			}
		   
		}
		catch(Exception e) {
		   return null;
		}
	}
	
	@GetMapping("/ping")
	public String ping() {
	    return "Auth service is alive!";
	}
	
	
	
}
