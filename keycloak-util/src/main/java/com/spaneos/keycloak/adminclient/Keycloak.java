package com.spaneos.keycloak.adminclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.spaneos.keycloak.adminclient.resource.RealmResource;
import com.spaneos.keycloak.adminclient.resource.RealmsResource;
import com.spaneos.keycloak.adminclient.resource.ServerInfoResource;
import com.spaneos.keycloak.adminclient.token.TokenManager;
import com.spaneos.keycloak.adminclient.util.BearerAuthFilter;

public class Keycloak {

	private final Config config;

	private final Client client;

	private final WebTarget target;

	Keycloak(String serverUrl, String realm, String username, String password, String clientId, String clientSecret,
			String grantType) {
		config = new Config(serverUrl, realm, username, password, clientId, clientSecret, grantType);
		client = ClientBuilder.newClient().register(JacksonFeature.class);
		target = client.target(config.getServerUrl());
		target.register(newAuthFilter());
	}

	private TokenManager getTokenManager() {
		return new TokenManager(config, client);
	}

	private BearerAuthFilter newAuthFilter() {
		return new BearerAuthFilter(getTokenManager());
	}

	public RealmsResource realms() {
		return WebResourceFactory.newResource(RealmsResource.class, target);
	}

	public RealmResource realm(String realmName) {
		return realms().realm(realmName);
	}

	public ServerInfoResource serverInfo() {
		return WebResourceFactory.newResource(ServerInfoResource.class, target);
	}

	public TokenManager tokenManager() {
		return getTokenManager();
	}

	public void close() {
		client.close();
	}

}