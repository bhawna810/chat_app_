package com.example.authentication.modal;

//package com.example.auth_service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userinfo")
public class LoginModal implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String image;
    
//    @Column(name = "isAdmin")
    private String isadmin;
   

    public LoginModal() {}

    public LoginModal(String username, String password, String email, String image, String isadmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
        this.isadmin = isadmin;
        
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // no roles for now
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
//    @Override
    public String getEmail() {
        return email;
    }
    
//    @Override
    public String getIsadmin() {
        return isadmin;
    }
    
//    @Override
    public String getImage() {
        return image;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setIsadmin(String isadmin) {
        this.isadmin = isadmin;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
}


