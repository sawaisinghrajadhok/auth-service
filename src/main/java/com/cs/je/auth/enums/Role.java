/**
 * 
 */
package com.cs.je.auth.enums;

/**
 * @author sawai
 *
 */

public enum Role {
	ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN"), CANDIDATE("CANDIDATE"), ROLE_SUPER_USER("ROLE_SUPER_USER");
	
	private String role;
	
	private Role(String role) {
		this.role = role;
	}
	
	public String getValue() {
		return role;
	}
}
