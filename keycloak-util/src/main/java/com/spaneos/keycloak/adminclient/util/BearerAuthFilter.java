package com.spaneos.keycloak.adminclient.util;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.HttpHeaders;

import com.spaneos.keycloak.adminclient.token.TokenManager;

public class BearerAuthFilter implements ClientRequestFilter, ClientResponseFilter {

	public static final String AUTH_HEADER_PREFIX = "Bearer ";
	private final TokenManager tokenManager;

	public BearerAuthFilter(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		String authHeader = AUTH_HEADER_PREFIX + tokenManager.getAccessTokenString();
		requestContext.getHeaders().remove(HttpHeaders.AUTHORIZATION);
		requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, authHeader);
	}

	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		if (responseContext.getStatus() == 401 && tokenManager != null) {
			List<Object> authHeaders = requestContext.getHeaders().get(HttpHeaders.AUTHORIZATION);
			if (authHeaders == null) {
				return;
			}
			for (Object authHeader1 : authHeaders) {
				if (authHeader1 instanceof String) {
					String headerValue = (String) authHeader1;
					if (headerValue.startsWith(AUTH_HEADER_PREFIX)) {
						String token = headerValue.substring(AUTH_HEADER_PREFIX.length());
						tokenManager.invalidate(token);
					}
				}
			}
		}
	}
}