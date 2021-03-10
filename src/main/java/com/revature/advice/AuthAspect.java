package com.revature.advice;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.annotations.Authorized;
import com.revature.exceptions.NotAuthorizedException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.Role;
import com.revature.models.User;

@Component
@Aspect
public class AuthAspect {

	@Autowired
	private HttpServletRequest req;

	// Advice: before after and around
	// after has two sub types: afterReturning & afterThrowing
	// Each of these has an annotation
	// Around is considered the most powerful, but also the most complex

	@Around("@annotation(authorized)") // Describes the point Cut
	public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {
		HttpSession session = req.getSession(false);
		//gets the session associated with this request
		//don't create one if it does not exist
		
		//if there is no session or if a session exist but no one is logged in
		if(session == null || session.getAttribute("user") == null) {
			throw new NotLoggedInException("Must be logged in to perform this action");
		}
		
		//after this point, there is a user that is logged in
		
		User u = (User) session.getAttribute("user");
		
		Role currentRole = u.getRole();
		
		List<Role> allowedRoles = Arrays.asList(authorized.allowedRoles());
		
		if(!allowedRoles.contains(currentRole)) {
			//the user is logged in but their role does not match
			// the list of allowed roles
			MDC.put("UserId", u.getId());
			throw new NotAuthorizedException("You are not authorized to perform this action");
		}
		
		return pjp.proceed(pjp.getArgs());
	}
}