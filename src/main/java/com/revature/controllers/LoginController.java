package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.revature.models.LoginTemplate;
import com.revature.models.User;
import com.revature.services.AuthorizationService;
import com.revature.services.UserService;

@Controller
public class LoginController {

	@Autowired
	private HttpServletResponse res;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorizationService authorizationService;

	@GetMapping("/login")
	public String loginForm(Model model) {
		if (authorizationService.alreadyLoggedIn()) {
			try {
				User u = userService.getFromSession();
				res.sendRedirect("/dungeons-and-dragons/users/" + u.getUsername());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("loginTemplate", new LoginTemplate());
		return "login";
	}

	@PostMapping("/login")
	public void loginSubmit(@ModelAttribute LoginTemplate loginTemplate) {
		User u = userService.login(loginTemplate.getEmail(), loginTemplate.getPassword());
		try {
			res.sendRedirect("/dungeons-and-dragons/users/" + u.getUsername());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/logout")
	public void logout(){
		userService.logout();
		try {
			res.sendRedirect("/dungeons-and-dragons");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
