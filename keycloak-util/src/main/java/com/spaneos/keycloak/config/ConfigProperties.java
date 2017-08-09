package com.spaneos.keycloak.config;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "keycloak")
public class ConfigProperties {

	@NotBlank
	private String serverUrl;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	private String clientId = "admin-cli";

	private String clientSecret = null;

	private String realm = "master";
	private int connectionPoolSize = 10;

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
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

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}

	@Override
	public String toString() {
		return "ConfigProperties [serverUrl=" + serverUrl + ", username=" + username + ", password=" + password
				+ ", clientId=" + clientId + ", clientSecret=" + clientSecret + ", realm=" + realm
				+ ", connectionPoolSize=" + connectionPoolSize + "]";
	}
}