/**
 * 
 */
package com.cs.je.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cs.je.auth.constant.Constants;
import com.cs.je.auth.enums.Role;
import com.cs.je.auth.model.User;
import com.cs.je.auth.service.UserService;


/**
 * @author sawai
 *
 */

@RestController
@RequestMapping(value = "/api/candidate")
public class CandidateController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public User user(@RequestBody User user) {
		user.setRole(Role.CANDIDATE);
		user.setTenantId(Constants.CANDIDATE_TENANT_ID);
		return userService.create(user);
	}
}




