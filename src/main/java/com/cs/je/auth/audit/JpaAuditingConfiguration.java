package com.cs.je.auth.audit;
/**
 * 
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import com.cs.je.auth.model.User;


/**
 * @author sawai
 *
 */

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfiguration {
	
	@Bean
	public AuditorAware<User> auditorAware() {
		return ()-> {
			if (SecurityContextHolder.getContext().getAuthentication() == null)  {
				return null;
			}
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return user;
		};
	}
}
