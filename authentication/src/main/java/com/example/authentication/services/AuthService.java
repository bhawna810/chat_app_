package com.example.authentication.services;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

//import modaljava.AuthRepository;
//
//import com.example.auth_service.JwtResponse;


//Lombok import
import lombok.RequiredArgsConstructor;

import com.example.authentication.repository.LoginRepository;
import com.example.authentication.modal.JwtResponse;
import com.example.authentication.modal.LoginModal;
import com.example.authentication.services.JwtService;

@Service
public class AuthService {
	
	@Autowired
	public LoginRepository LoginRepository;
//	
	@Autowired
	public JwtService JwtService;
	
    public String getaccessTokenAgain(String username ,String password){
		
		try {
			LoginModal user = LoginRepository.findByUsernameAndPassword(username, password);
			if(user == null) {
				 return null;
			}
			
			System.out.println("user "+ user);
			
			String jwttoken = JwtService.generateToken(user);
			
			System.out.println("jwttoken "+ jwttoken);
			
			System.out.println("user" + user);
			
			return jwttoken;
		}
		catch(Exception e) {
			System.out.println("inside catch method of getaccessTokenAgain "+ e);
			 return "some error"+e;
		}
		
	}
}


