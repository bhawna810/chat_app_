package com.example.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authentication.modal.LoginModal;


@Repository
public interface LoginRepository extends JpaRepository<LoginModal, Long>{
	
	LoginModal findByUsernameAndPassword(String username, String password);
	LoginModal findByUsername(String username);
	
}
