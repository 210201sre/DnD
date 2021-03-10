package com.revature.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.User;

public interface UserDAO extends JpaRepository<User, Integer>{

	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByEmail(String email);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);

}
