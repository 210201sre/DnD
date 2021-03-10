package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.revature.models.RegisterTemplate;
import com.revature.models.User;
import com.revature.services.AuthorizationService;
import com.revature.services.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	private HttpServletResponse res;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		if (authorizationService.alreadyLoggedIn()) {
			try {
				User u = userService.getFromSession();
				res.sendRedirect("/dungeons-and-dragons/users/" + u.getUsername());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("registerTemplate", new RegisterTemplate());
		return "register";
	}
	
	@PostMapping("/register")
	public void register(@ModelAttribute RegisterTemplate registerTemplate){
		User u = userService.register(registerTemplate);
		userService.login(u.getEmail(), u.getPassword());
		try {
			res.sendRedirect("/dungeons-and-dragons/users/"+u.getUsername());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
