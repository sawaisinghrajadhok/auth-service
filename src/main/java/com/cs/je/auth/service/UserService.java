/**
 * 
 */
package com.cs.je.auth.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cs.je.auth.model.User;

/**
 * @author sawai
 *
 */

public interface UserService extends UserDetailsService {

	public User create(User user);
	
	public User findOne(long id);
	
	public List<User> getAll();
	
}
