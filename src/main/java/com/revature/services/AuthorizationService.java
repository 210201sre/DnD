package com.revature.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.NotAuthorizedException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;

@Service
public class AuthorizationService {

	@Autowired
	private HttpServletRequest req;
	
	public User guardByUsername(String username) {
		HttpSession session = req.getSession(false);
		
		if(session == null || session.getAttribute("user") == null)
			throw new NotLoggedInException();
		
		User u = (User) session.getAttribute("user");
		
		if(!u.getUsername().equals(username)) 
			throw new NotAuthorizedException();
		
		return u;
		
	}
	
	public boolean alreadyLoggedIn() {
		HttpSession session = req.getSession(false);
		
		if(session == null || session.getAttribute("user") == null)
			return false;
		
		return true;
	}
}
