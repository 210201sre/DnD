package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Authorized(allowedRoles= {Role.ADMIN})
	@GetMapping("/users")
	public ResponseEntity<List<User>> findAll(){
		return ResponseEntity.ok(adminService.findAll());
	}
	
	@Authorized(allowedRoles= {Role.ADMIN})
	@GetMapping("/users/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") int id){
		return ResponseEntity.ok(adminService.findById(id));
	}
	
	@Authorized(allowedRoles= {Role.ADMIN})
	@GetMapping("/loggedin")
	public ResponseEntity<List<User>> getAllLoggedInUsers(){
		return ResponseEntity.ok(adminService.getAllLoggedInUsers());
	}
	
	@Authorized(allowedRoles= {Role.ADMIN})
	@PutMapping("/logout/{id}")
	public ResponseEntity<Void> logoutById(@PathVariable("id") int id){
		if(adminService.forceLogout(id)) {
			return ResponseEntity.accepted().build();
		}
		return ResponseEntity.noContent().build();
	}
	
	@Authorized(allowedRoles= {Role.ADMIN})
	@PutMapping("/logoutAll")
	public ResponseEntity<Void> logoutAll(){
		if(adminService.forcelogoutAll()) {
			return ResponseEntity.accepted().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@Authorized(allowedRoles= {Role.ADMIN})
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") int id){
		if(adminService.delete(id)) {
			return ResponseEntity.accepted().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
