package com.revature.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.PasswordMismatchException;
import com.revature.exceptions.UnsuccessfulLoginException;
import com.revature.exceptions.UsernameAlreadyExistException;
import com.revature.models.DnDCharacter;
import com.revature.models.DungeonMaster;
import com.revature.models.RegisterTemplate;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

@Service
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private HttpServletRequest req;

	@Autowired
	private UserDAO userDAO;
	
	public String onlineFriends(User u) {
		StringBuilder sb = new StringBuilder();
		Map<User, HttpSession> loggedIn = User.getAllLoggedInUsers();
		for(Map.Entry<User, HttpSession> user: loggedIn.entrySet()) {
			if(u.getFriendId().contains(user.getKey().getId())) {
				sb.append(user.getKey().getUsername() + "\n");
			}
		}
		
		return sb.toString();
	}

	public User update(User u) {
		if (!userDAO.existsById(u.getId())) {
			// make into custom exception
			throw new RuntimeException("User must already exist to update");
		}

		userDAO.save(u);

		return u;
	}

	public static void update(User u, String message) {
	}

	
	public boolean delete(int id) {
		if (!userDAO.existsById(id)) {
			return false;
		}

		userDAO.deleteById(id);
		req.getSession(false).invalidate();
		
		MDC.put("UserId", Integer.toString(id));
		log.info("User has deleted their account");
		MDC.clear();

		return true;

	}

	public User login(String email, String password) {
		User u = userDAO.findByEmail(email)
				.orElseThrow(() -> new UnsuccessfulLoginException("Email or password is wrong"));

		if (!u.getPassword().equals(password)) {
			throw new UnsuccessfulLoginException("Email or password is wrong");
		}
		setScript(u);
		
		HttpSession session = req.getSession();
		req.changeSessionId();
		session.setAttribute("user", u);
		
		MDC.put("UserId", Integer.toString(u.getId()));
		log.info("User has logged in");
		MDC.clear();

		return u;
	}

	public void logout() {
		HttpSession session = req.getSession(false);
		
		if(session == null) 
			return;
		
		session.invalidate();

	}

	public User register(RegisterTemplate registerTemplate) {
		if(!registerTemplate.getPassword().equals(registerTemplate.getPasswordRetyped()))
			throw new PasswordMismatchException();
		
		if(userDAO.existsByUsername(registerTemplate.getUsername()))
			throw new UsernameAlreadyExistException();
			
		if(userDAO.existsByEmail(registerTemplate.getEmail()))
			throw new EmailAlreadyExistsException();
		DungeonMaster dm = new DungeonMaster(0, 0.0f, null);
		User u = new User(0, registerTemplate.getUsername(), registerTemplate.getEmail(), registerTemplate.getPassword(), Role.PLAYER, dm, new ArrayList<>(), new HashSet<>());
		dm.setOwner(u);
		userDAO.save(u);
		MDC.put("UserId", Integer.toString(u.getId()));
		log.info("New user has been registered");
		MDC.clear();
		return u;
	}
	
	public User getFromSession() {
		HttpSession session = req.getSession(false);
		if(session == null || session.getAttribute("user") == null)
			throw new NotLoggedInException();
		
		return (User) session.getAttribute("user");
	}

	private void setScript(User u) {
		for (DnDCharacter c : u.getDndCharacters()) {
			c.setScripts(c.getLanguages());
		}
	}
}
