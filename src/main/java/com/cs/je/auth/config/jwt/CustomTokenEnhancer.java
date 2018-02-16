/**
 * 
 */
package com.cs.je.auth.config.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.cs.je.auth.model.User;

/**
 * @author sawai
 *
 */

public class CustomTokenEnhancer implements TokenEnhancer {
	
	@Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        if (authentication != null) {
            User user = (User)authentication.getPrincipal();
            additionalInfo.put("email", user.getEmail());
            additionalInfo.put("tenant", user.getTenantId());
            additionalInfo.put("id", user.getId());
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
