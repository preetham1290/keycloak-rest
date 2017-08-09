package com.spaneos.keycloak.adminclient.token;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;
import static org.keycloak.OAuth2Constants.CLIENT_ID;
import static org.keycloak.OAuth2Constants.GRANT_TYPE;
import static org.keycloak.OAuth2Constants.PASSWORD;
import static org.keycloak.OAuth2Constants.REFRESH_TOKEN;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.keycloak.common.util.Time;
import org.keycloak.representations.AccessTokenResponse;

import com.spaneos.keycloak.adminclient.Config;
import com.spaneos.keycloak.adminclient.util.BasicAuthFilter;

public class TokenManager {
	private static final long DEFAULT_MIN_VALIDITY = 30;

	private AccessTokenResponse currentToken;
	private long expirationTime;
	private long minTokenValidity = DEFAULT_MIN_VALIDITY;
	private final Config config;
	private final String accessTokenGrantType;
	private final TokenService tokenService;

	public TokenManager(Config config, Client client) {
		this.config = config;
		this.accessTokenGrantType = config.getGrantType();
		WebTarget target = client.target(config.getServerUrl());
		if (!config.isPublicClient()) {
			target.register(new BasicAuthFilter(config.getClientId(), config.getClientSecret()));
		}
		this.tokenService = WebResourceFactory.newResource(TokenService.class, target);
		if (CLIENT_CREDENTIALS.equals(accessTokenGrantType) && config.isPublicClient()) {
			throw new IllegalArgumentException(
					"Can't use " + GRANT_TYPE + "=" + CLIENT_CREDENTIALS + " with public client");
		}
	}

	public String getAccessTokenString() {
		return getAccessToken().getToken();
	}

	public synchronized AccessTokenResponse getAccessToken() {
		if (currentToken == null) {
			grantToken();
		} else if (tokenExpired()) {
			refreshToken();
		}
		return currentToken;
	}

	public AccessTokenResponse grantToken() {

		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add(GRANT_TYPE, accessTokenGrantType);
		if (PASSWORD.equals(accessTokenGrantType)) {
			map.add("username", config.getUsername());
			map.add("password", config.getPassword());
		}

		if (config.isPublicClient()) {
			map.add(CLIENT_ID, config.getClientId());
		}

		int requestTime = Time.currentTime();
		synchronized (this) {
			currentToken = tokenService.grantToken(config.getRealm(), map);
			expirationTime = requestTime + currentToken.getExpiresIn();
		}
		return currentToken;
	}

	public AccessTokenResponse refreshToken() {

		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add(GRANT_TYPE, REFRESH_TOKEN);
		map.add(REFRESH_TOKEN, currentToken.getRefreshToken());

		if (config.isPublicClient()) {
			map.add(CLIENT_ID, config.getClientId());
		}

		try {
			int requestTime = Time.currentTime();

			synchronized (this) {
				currentToken = tokenService.refreshToken(config.getRealm(), map);
				expirationTime = requestTime + currentToken.getExpiresIn();
			}
			return currentToken;
		} catch (Exception e) {
			return grantToken();
		}
	}

	public synchronized void setMinTokenValidity(long minTokenValidity) {
		this.minTokenValidity = minTokenValidity;
	}

	private synchronized boolean tokenExpired() {
		return (Time.currentTime() + minTokenValidity) >= expirationTime;
	}

	/**
	 * Invalidates the current token, but only when it is equal to the token
	 * passed as an argument.
	 *
	 * @param token
	 *            the token to invalidate (cannot be null).
	 */
	public void invalidate(String token) {
		if (currentToken == null) {
			return; // There's nothing to invalidate.
		}
		if (token.equals(currentToken.getToken())) {
			// When used next, this cause a refresh attempt, that in turn will
			// cause a grant attempt if refreshing fails.
			expirationTime = -1;
		}
	}
}