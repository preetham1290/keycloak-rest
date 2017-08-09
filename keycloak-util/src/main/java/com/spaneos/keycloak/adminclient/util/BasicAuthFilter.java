package com.spaneos.keycloak.adminclient.util;

import java.io.IOException;
import java.util.Base64;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

public class BasicAuthFilter implements ClientRequestFilter {
	private String userName;

	private String password;

	public BasicAuthFilter(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		String pair = userName + ":" + password;
		String authHeader = "Basic " + new String(Base64.getEncoder().encode(pair.getBytes()));
		requestContext.getHeaders().remove(HttpHeaders.AUTHORIZATION);
		requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, authHeader);
	}
}