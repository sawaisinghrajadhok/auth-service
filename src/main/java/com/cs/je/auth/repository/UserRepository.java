/**
 * 
 */
package com.cs.je.auth.repository;

/**
 * @author sawai
 *
 */

import org.springframework.data.repository.query.Param;

import com.cs.je.auth.model.User;

public interface UserRepository extends BaseRepository<User, Long> {
	
	public User findByEmail(@Param("email") String email);
	    
	
}