package com.cs.je.auth.config.jwt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.cs.je.auth.enums.Role;
import com.cs.je.auth.model.User;
/**
 * @author sawai
 *
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private int accessTokenValiditySeconds = 120;
    private int refreshTokenValiditySeconds = 30000;

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomAccessTokenConverter customAccessTokenConverter;
 
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	
    	endpoints
                .authenticationManager(this.authenticationManager)
                .tokenServices(tokenServices())
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    	
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
        // we're allowing access to the token only for clients with 'ROLE_TRUSTED_CLIENT' authority
                //.tokenKeyAccess("permitAll()")
        		.tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("trusted-app")
                    .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                    .authorities("ROLE_TRUSTED_CLIENT")
                    .scopes("read", "write")
                    .resourceIds(resourceId)
                    .accessTokenValiditySeconds(accessTokenValiditySeconds)
                    .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                    .secret("secret");
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    	
    	DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
    	tokenConverter.setUserTokenConverter(new DefaultUserAuthenticationConverter() {
    		@Override
    	    public Authentication extractAuthentication(Map<String, ?> map) {
    	        Authentication authentication = super.extractAuthentication(map);
    	        
    	        // User is my custom UserDetails class
    	        User user = new User();
    	        user.setTenantId(map.get("tenant").toString());
    	        user.setEmail(map.get("email").toString());
    	        user.setId(map.get("id").toString());
    	        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    	        authorities.addAll(authentication.getAuthorities());
    	        user.setGrantedAuthorities(authorities);
    	        user.setRole(Role.valueOf(authorities.iterator().next().toString()));
    	        //user.setSpecialKey(map.get("specialKey").toString());
    	        return new UsernamePasswordAuthenticationToken(user,
    	                authentication.getCredentials(), authentication.getAuthorities());
    	    }
    	});
    	
    	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("key");
        converter.setAccessTokenConverter(customAccessTokenConverter);
        converter.setAccessTokenConverter(tokenConverter);
        return converter;
    }    
    
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
    	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    	tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
    	DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        return defaultTokenServices;
    }    
}