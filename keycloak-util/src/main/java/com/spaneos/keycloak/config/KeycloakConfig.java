package com.spaneos.keycloak.config;

import static org.keycloak.OAuth2Constants.PASSWORD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spaneos.keycloak.adminclient.Keycloak;
import com.spaneos.keycloak.adminclient.KeycloakBuilder;
@Configuration
public class KeycloakConfig {

	@Autowired
	private ConfigProperties configProperties;

	@Bean
	public Keycloak getKeyCloak() {
		Keycloak kc = KeycloakBuilder.builder().serverUrl(configProperties.getServerUrl())
				.realm(configProperties.getRealm()).username(configProperties.getUsername())
				.password(configProperties.getPassword()).clientId(configProperties.getClientId())
				.clientSecret(configProperties.getClientSecret()).grantType(PASSWORD).build();
		return kc;
	}
}
