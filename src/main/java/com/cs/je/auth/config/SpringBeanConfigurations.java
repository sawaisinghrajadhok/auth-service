package com.cs.je.auth.config;
/**
 * 
 */

 
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
/*import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;*/
 

/**
 * @author sawai
 *
 */

@Configuration
public class SpringBeanConfigurations {
		
	/*@Bean
    public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
    }*/
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
		bundleMessageSource.setBasename("messages/messages");
		return bundleMessageSource;
	}
	
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
	    return new SecurityEvaluationContextExtension();
	}
}
