package com.example.authentication.modal;

//public class JwtResponse {
//
//}



//package com.example.auth_service;

public class JwtResponse {
   
	 private String jwttoken;
	 private LoginModal user;
	 
//	 private String jwtRefreshtoken;
	 
	 public JwtResponse(String jwttoken, LoginModal user){
		 this.jwttoken = jwttoken;
		 this.user = user;
//	     this.jwtRefreshtoken = jwtRefreshtoken;
	 }
	 
	 public String getjwttoken(){
		 return jwttoken;
	 }
	 
	 public LoginModal getUser(){
		 return user;
	 }
	 
//     public String getjwtRefreshtoken(){
//		 return jwtRefreshtoken;
//	 }
}
