package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.models.User;
import com.revature.services.AuthorizationService;
import com.revature.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/users/{username}")
	public String usernameHomePage(@PathVariable("username") String username, Model model) {
		User u = authorizationService.guardByUsername(username);
		model.addAttribute("username", username);
		model.addAttribute("friends", userService.onlineFriends(u));
		return "userpage";
	}
	@GetMapping("/users/{usrename}/settings")
	public String settings(@PathVariable("username") String username, Model model) {
		User u = authorizationService.guardByUsername(username);
		model.addAttribute("user", u);
		return "settings";
	}
	
	@GetMapping("/users/{username}/info")
	public ResponseEntity<User> findByUsername(@PathVariable("username") String username) {
		User u = authorizationService.guardByUsername(username);
		return ResponseEntity.ok(u);
	}
	
	@DeleteMapping("/users/{username}/delete")
	public ResponseEntity<Void> deleteById(@PathVariable("username") String username){
		User u = authorizationService.guardByUsername(username);
		if(userService.delete(u.getId())) {
			return ResponseEntity.accepted().build();
		}
		return ResponseEntity.noContent().build();
	}
}
