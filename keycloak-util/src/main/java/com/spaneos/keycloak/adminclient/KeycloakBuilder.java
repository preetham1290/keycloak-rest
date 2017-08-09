package com.spaneos.keycloak.adminclient;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;
import static org.keycloak.OAuth2Constants.PASSWORD;

public class KeycloakBuilder {
	private String serverUrl;
	private String realm;
	private String username;
	private String password;
	private String clientId;
	private String clientSecret;
	private String grantType;

	public KeycloakBuilder serverUrl(String serverUrl) {
		this.serverUrl = serverUrl;
		return this;
	}

	public KeycloakBuilder realm(String realm) {
		this.realm = realm;
		return this;
	}

	public KeycloakBuilder grantType(String grantType) {
		Config.checkGrantType(grantType);
		this.grantType = grantType;
		return this;
	}

	public KeycloakBuilder username(String username) {
		this.username = username;
		return this;
	}

	public KeycloakBuilder password(String password) {
		this.password = password;
		return this;
	}

	public KeycloakBuilder clientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	public KeycloakBuilder clientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}
	
	/**
	 * Builds a new Keycloak client from this builder.
	 */
	public Keycloak build() {
		if (serverUrl == null) {
			throw new IllegalStateException("serverUrl required");
		}

		if (realm == null) {
			throw new IllegalStateException("realm required");
		}

		if (grantType == null) {
			grantType = PASSWORD;
		}

		if (PASSWORD.equals(grantType)) {
			if (username == null) {
				throw new IllegalStateException("username required");
			}

			if (password == null) {
				throw new IllegalStateException("password required");
			}
		} else if (CLIENT_CREDENTIALS.equals(grantType)) {
			if (clientSecret == null) {
				throw new IllegalStateException("clientSecret required with grant_type=client_credentials");
			}
		}

		if (clientId == null) {
			throw new IllegalStateException("clientId required");
		}

		return new Keycloak(serverUrl, realm, username, password, clientId, clientSecret, grantType);
	}

	private KeycloakBuilder() {
	}

	/**
	 * Returns a new Keycloak builder.
	 */
	public static KeycloakBuilder builder() {
		return new KeycloakBuilder();
	}
}