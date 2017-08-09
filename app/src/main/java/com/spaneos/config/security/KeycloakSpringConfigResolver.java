package com.spaneos.config.security;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.spaneos.keycloak.service.KeycloakConfigResolverService;

public class KeycloakSpringConfigResolver implements KeycloakConfigResolver {

	@Autowired
	private KeycloakConfigResolverService service;

	public static final Logger LOGGER = LoggerFactory.getLogger(KeycloakSpringConfigResolver.class);

	@Override
	public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
		InputStream is = null;
		try {
			is = service.getClientConfigIS(request.getURI());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return KeycloakDeploymentBuilder.build(is);
	}

}