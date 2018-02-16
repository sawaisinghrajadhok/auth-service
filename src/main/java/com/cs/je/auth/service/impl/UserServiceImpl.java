/**
 * 
 */
package com.cs.je.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cs.je.auth.model.User;
import com.cs.je.auth.repository.UserRepository;
import com.cs.je.auth.service.UserService;
import com.cs.je.auth.utils.UserUtils;

/**
 * @author sawai
 *
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserUtils userUtils;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user != null) {
			Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
			user.setGrantedAuthorities(grantedAuthorities);
		}
		return user;
	}
	
	@Override
	public User create(User user) {
		String encodedPassword = userUtils.passwordEncoder(user.getPassword());
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}

	@Override
	public User findOne(long id) {
		return userRepository.findOne(id);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}
}
