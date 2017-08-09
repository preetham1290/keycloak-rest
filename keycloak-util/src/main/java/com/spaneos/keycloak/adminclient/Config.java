package com.spaneos.keycloak.adminclient;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;
import static org.keycloak.OAuth2Constants.PASSWORD;

public class Config {

	private String serverUrl;
	private String realm;
	private String username;
	private String password;
	private String clientId;
	private String clientSecret;
	private String grantType;

	public Config(String serverUrl, String realm, String username, String password, String clientId,
			String clientSecret) {
		this(serverUrl, realm, username, password, clientId, clientSecret, PASSWORD);
	}

	public Config(String serverUrl, String realm, String username, String password, String clientId,
			String clientSecret, String grantType) {
		this.serverUrl = serverUrl;
		this.realm = realm;
		this.username = username;
		this.password = password;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.grantType = grantType;
		checkGrantType(grantType);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public boolean isPublicClient() {
		return clientSecret == null;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
		checkGrantType(grantType);
	}

	public static void checkGrantType(String grantType) {
		if (grantType != null && !PASSWORD.equals(grantType) && !CLIENT_CREDENTIALS.equals(grantType)) {
			throw new IllegalArgumentException("Unsupported grantType: " + grantType + " (only " + PASSWORD + " and "
					+ CLIENT_CREDENTIALS + " are supported)");
		}
	}

	@Override
	public String toString() {
		return "Config [serverUrl=" + serverUrl + ", realm=" + realm + ", username=" + username + ", password="
				+ password + ", clientId=" + clientId + ", clientSecret=" + clientSecret + ", grantType=" + grantType
				+ "]";
	}
}