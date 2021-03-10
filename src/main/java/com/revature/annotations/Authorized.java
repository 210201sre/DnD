package com.revature.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.revature.models.Role;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Authorized {

	public Role[] allowedRoles() default {};
	// this annotaiton will have allowedRoles field that is of type Role[]
	// if this annotation is not provided a value for this field it will
	// have an empty array
}
