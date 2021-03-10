package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterTemplate {
	private String username;
	private String email;
	private String password;
	private String passwordRetyped;
}
