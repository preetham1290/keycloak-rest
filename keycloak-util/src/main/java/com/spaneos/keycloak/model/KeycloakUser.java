package com.spaneos.keycloak.model;

import java.util.ArrayList;
import java.util.List;

public class KeycloakUser {

	private String keyCloakUserId;
	private String userName;
	private String email;
	private String password;
	private boolean temporary = false;
	private boolean enabled = true;

	private List<String> roleIds = new ArrayList<>();

	public String getKeyCloakUserId() {
		return keyCloakUserId;
	}

	public void setKeyCloakUserId(String keyCloakUserId) {
		this.keyCloakUserId = keyCloakUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "KeycloakUser [keyCloakUserId=" + keyCloakUserId + ", userName=" + userName + ", email=" + email
				+ ", password=" + password + ", temporary=" + temporary + ", enabled=" + enabled + ", roleIds="
				+ roleIds + "]";
	}
}
