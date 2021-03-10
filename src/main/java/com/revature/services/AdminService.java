package com.revature.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.controllers.AdminController;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.DnDCharacter;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

@Service
public class AdminService {
	
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private HttpServletRequest req;
	
	@Autowired
	private UserDAO userDAO;

	public List<User> findAll() {
		List<User> users = userDAO.findAll();
		for(User u : users) {
			setScript(u);
		}
		User admin = (User) req.getSession().getAttribute("user");
		MDC.put("AdminId", Integer.toString(admin.getId()));
		log.info("Admin requested information on all users");
		MDC.clear();
		return users;
	}

	public User findById(int id) {

		User u = userDAO.findById(id)
				.orElseThrow(() -> new UserNotFoundException(String.format("No user with id = %d", id)));
		setScript(u);
		
		User admin = (User) req.getSession().getAttribute("user");
		MDC.put("AdminId", Integer.toString(admin.getId()));
		log.info("Admin requested information on user {}", id);
		MDC.clear();
		return u;
	}
	
	public boolean delete(int id) {
		
		User admin = (User) req.getSession().getAttribute("user");
		MDC.put("AdminId", Integer.toString(admin.getId()));
		
		if(!userDAO.existsById(id)) {
			log.warn("Admin tried to delete user with id: {}, but no such user exists", id);
			MDC.clear();
			return false;
		}
		
		userDAO.deleteById(id);
		log.info("Admin deleted user with id: {}", id);
		MDC.clear();
		
		return true;
	}
	
	public boolean forceLogout(int id) {
		Map<User, HttpSession> loggedIn = User.getAllLoggedInUsers();
		Optional<User> u = loggedIn.keySet().stream().filter(s -> s.getId() == id).findFirst();
		
		User admin = (User) req.getSession().getAttribute("user");
		MDC.put("AdminId", Integer.toString(admin.getId()));
		
		if(!u.isPresent()) {
			log.warn("Admin tried to forcfully log out user {}, but user is already logged out", id);
			MDC.clear();
			return false;
		}
		userDAO.save(u.get());
		loggedIn.get(u.get()).invalidate();
		log.info("Admin forcfully logged out user {}", id);
		MDC.clear();
		return true;
	}
	
	public boolean forcelogoutAll() {
		Map<User, HttpSession> loggedIn = User.getAllLoggedInUsers();
		User admin = (User) req.getSession().getAttribute("user");
		MDC.put("AdminId", Integer.toString(admin.getId()));
		
		if(loggedIn.isEmpty()) {
			log.info("Admin tried forcfully logged out all users, but no users are logged on");
			MDC.clear();
			return false;
		}
		
		for(Map.Entry<User, HttpSession> user : loggedIn.entrySet()) {
			userDAO.save(user.getKey());
			user.getValue().invalidate();
		}

		log.info("Admin forcfully logged out all users");
		MDC.clear();
		
		return true;
	}
	
	public List<User> getAllLoggedInUsers() {
		User admin = (User) req.getSession().getAttribute("user");
		MDC.put("AdminId", Integer.toString(admin.getId()));
		log.info("Admin requested information on users currently logged in");
		MDC.clear();
		return User.getAllLoggedInUsers().keySet().stream().collect(Collectors.toList());
	}
	
	private void setScript(User u) {
		for (DnDCharacter c : u.getDndCharacters()) {
			c.setScripts(c.getLanguages());
		}
	}

}
