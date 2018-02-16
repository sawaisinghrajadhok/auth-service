/**
 * 
 */
package com.cs.je.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cs.je.auth.model.User;
import com.cs.je.auth.service.UserService;

/**
 * @author sawai
 *
 */

@Component
public class UserUtils {
	
	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	@Autowired
	private UserService userService;
	
	public User getCurrentUser() {
		String email = (String)JWTUtils.getCustomValues().get("email");
		return (User)userService.loadUserByUsername(email);
	}
	
	public String passwordEncoder(String password) {
		return PASSWORD_ENCODER.encode(password);
	} 
}
