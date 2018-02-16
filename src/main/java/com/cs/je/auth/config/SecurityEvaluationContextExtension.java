/**
 * 
 */
package com.cs.je.auth.config;

import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author sawai
 *
 */

public class SecurityEvaluationContextExtension extends EvaluationContextExtensionSupport {

	@Override
	public String getExtensionId() {
		return "sawai";
	}
	
	@Override
	  public SecurityExpressionRoot getRootObject() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return new SecurityExpressionRoot(authentication) {};
	  }
}




