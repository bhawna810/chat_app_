package com.example.authentication.controller;


import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

//Lombok import
import lombok.RequiredArgsConstructor;

import com.example.authentication.repository.LoginRepository;
import com.example.authentication.modal.JwtResponse;
import com.example.authentication.modal.LoginModal;
import com.example.authentication.services.JwtService;

@RestController
@RequestMapping("/auth")
public class RefreshTokenController {

	 @Autowired
	 private JwtService JwtService;
//	 
	 @Autowired
	 private UserDetailsService userDetailsService;
	 
		@Autowired
		private LoginRepository loginRepository ;

	 
	 @GetMapping("/refresh")
	 public ResponseEntity<JwtResponse> relogin( @CookieValue(value = "jwtResfreshtoken", required = false) String refreshToken) {

		 try {
			 
//			 System.out.println(" refreshToken " + refreshToken);
             String username = JwtService.extractUsernameRefresh(refreshToken);
			 
			 UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			 
//			 System.out.println(" userDetails " + userDetails);
			 
			 if (refreshToken == null || !JwtService.isTokenValidRefresh(refreshToken, userDetails)) {
				 
//				 System.out.println(" inside if ");
				 
			        return ResponseEntity.status(403).body(new JwtResponse(null, null));
			    }
			 
              
			 
			    LoginModal userValue = loginRepository.findByUsername(username);
//			    String jwttoken = JwtService.generateToken(userDetails);

		        String newAccessToken = JwtService.generateToken(userDetails);
		        
//		        System.out.println(" newAccessToken "+ newAccessToken);

		        return ResponseEntity.ok(new JwtResponse(newAccessToken , userValue));
			 
			}catch(Exception e){
				e.printStackTrace();
				return ResponseEntity.ok(new JwtResponse(null, null));
			}		 
	 }
}
