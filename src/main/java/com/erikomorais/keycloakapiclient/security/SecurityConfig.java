package com.erikomorais.keycloakapiclient.security;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;



@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true, 
		securedEnabled = true, 
		jsr250Enabled = true)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

	/**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
        grantedAuthorityMapper.setPrefix("ROLE_");
        grantedAuthorityMapper.setConvertToUpperCase(true);
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
        auth.authenticationProvider(keycloakAuthenticationProvider);
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }
    

   /*
    *  You must provide a session authentication strategy bean which should be of type 
    *  RegisterSessionAuthenticationStrategy for public or confidential applications and 
    *  NullAuthenticatedSessionStrategy for bearer-only applications.(non-Javadoc)
    * @see org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter#sessionAuthenticationStrategy()
    */
	
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new NullAuthenticatedSessionStrategy();
	}
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
        http
                .authorizeRequests()
                .anyRequest().permitAll();
    }
		
	
	/*
	 * Using Spring Boot Configuration
	 */
	
	@Bean
	public KeycloakConfigResolver KeycloakConfigResolver() {
	    return new KeycloakSpringBootConfigResolver();
	}
	/*
	 * Avoiding Spring Boot double registration
	 */
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
            KeycloakAuthenticationProcessingFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
            KeycloakPreAuthActionsFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean(
            KeycloakAuthenticatedActionsFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(
        KeycloakSecurityContextRequestFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }
	
	

}
