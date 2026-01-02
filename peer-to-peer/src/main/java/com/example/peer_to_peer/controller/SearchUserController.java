package com.example.peer_to_peer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import com.example.authentication.modal.LoginModal;

import jakarta.servlet.http.HttpServletResponse;



@RestController("/peer")
public class SearchUserController {

	@GetMapping("/search")
	public String SearchUserWithString(@RequestBody String a, HttpServletResponse response) {
		
	 	 
		
		
		
		return null;
	}
	
}
