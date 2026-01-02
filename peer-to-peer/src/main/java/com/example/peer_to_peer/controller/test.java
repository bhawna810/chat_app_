package com.example.peer_to_peer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/peer")
public class test {

	@GetMapping("/test")
	public String testing() {
		return "Welcome";
	}
}
