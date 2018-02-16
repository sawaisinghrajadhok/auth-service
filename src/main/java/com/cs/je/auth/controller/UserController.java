package com.cs.je.auth.controller;

/**
 * @author sawai
 *
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.cs.je.auth.model.User;
import com.cs.je.auth.service.UserService;
import com.cs.je.auth.utils.UserUtils;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserUtils userUtils;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public User create(@RequestBody User user) {
		return userService.create(user);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User findOne(@PathVariable("id") Long id) {
		return userService.findOne(id);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> getAll() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.getAll();
	}
}
