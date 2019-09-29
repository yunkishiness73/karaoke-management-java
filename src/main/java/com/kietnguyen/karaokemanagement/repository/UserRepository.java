package com.kietnguyen.karaokemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kietnguyen.karaokemanagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	 User findByUsername(String username);
}
