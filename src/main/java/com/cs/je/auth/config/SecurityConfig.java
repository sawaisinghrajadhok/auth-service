package com.cs.je.auth.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cs.je.auth.constant.Constants;
import com.cs.je.auth.enums.Role;
import com.cs.je.auth.model.User;
import com.cs.je.auth.service.UserService;
/**
 * @author sawai
 *
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Autowired
	private UserService userService;
	
	/*@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
		.antMatchers("/").permitAll()
		.antMatchers(HttpMethod.POST ,"/user/**").hasAnyAuthority("ROLE_SUPER_USER", "ROLE_ADMIN")
		.anyRequest().authenticated();
		
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
		httpSecurity.requestCache().requestCache(new NullRequestCache());
		httpSecurity.httpBasic();
		//httpSecurity.addFilterBefore(CORSFilter, ChannelProcessingFilter.class);
	}*/
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
		
		if (userService.loadUserByUsername("admin@email.com") == null) {
			User user = new User();
			user.setEmail("admin@email.com");
			user.setPassword("password");
			user.setTenantId(Constants.JE_TENANT_ID);
			user.setRole(Role.ROLE_SUPER_USER);
			userService.create(user);
		}
	 }
    
    @Override
    public void configure(WebSecurity webSecurity) {
    	webSecurity.ignoring().antMatchers("/api/candidate/**");
    }
}
