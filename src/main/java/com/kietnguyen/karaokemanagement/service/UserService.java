package com.kietnguyen.karaokemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kietnguyen.karaokemanagement.config.CustomUserDetails;
import com.kietnguyen.karaokemanagement.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Check if user is exists in database
		com.kietnguyen.karaokemanagement.model.User user = userRepository.findByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException("Username is not exists");
        }
		
		return new CustomUserDetails(user);
	}
	
	public com.kietnguyen.karaokemanagement.model.User currentUser(Authentication authentication) {
		CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
		
		return customUser.getUser();
    }
}
