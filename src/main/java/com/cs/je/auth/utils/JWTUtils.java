/**
 * 
 */
package com.cs.je.auth.utils;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @author sawai
 *
 */
public class JWTUtils {
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getCustomValues() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();        
		Object details = authentication.getDetails();        
	//	if ( details instanceof OAuth2AuthenticationDetails){
		    OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails)details;
		    return (Map<String, Object>)oAuth2AuthenticationDetails.getDecodedDetails();
		//} else {
			//System.out.println("details");
			//return null;
		//}
	}
}
