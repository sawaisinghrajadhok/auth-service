/**
 * 
 */
package com.cs.je.auth.core;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cs.je.auth.constant.Constants;
import com.cs.je.auth.enums.Role;
import com.cs.je.auth.model.User;
 

/**
 * @author sawai
 *
 */

@Component
@Aspect
public class MultitenancyAspect {
	
	@Before("execution(* com.cs.je.auth.repository..save(..)) && args(entity)")
	public void addTenant(User entity) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			User user = (User) authentication.getPrincipal();
			if (user.getRole().equals((Role.ROLE_SUPER_USER))) {
				entity.setTenantId(Constants.JE_TENANT_ID);
				return;
			} else if (user.getTenantId().equals(Constants.JE_TENANT_ID)) {
				return;
			}
			if (user.getTenantId() != null) {
				entity.setTenantId(user.getTenantId());
			} 
		}
	}
}



