package com.kietnguyen.karaokemanagement.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kietnguyen.karaokemanagement.config.CustomUserDetails;
import com.kietnguyen.karaokemanagement.model.User;
import com.kietnguyen.karaokemanagement.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    public User currentUser(Authentication authentication) {
		CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
		
		return customUser.getUser();
    }
}
