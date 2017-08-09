package com.spaneos.keycloak.service;

import java.util.List;

import com.spaneos.keycloak.model.KeyCloakRole;
import com.spaneos.keycloak.model.KeycloakUser;

public interface KeycloakUserService {

	String create(KeycloakUser keycloakUser, String realm);

	void updatePassword(KeycloakUser keycloakUser, String realm);

	void update(KeycloakUser keycloakUser, String realm);

	KeycloakUser getUser(String realm, String userId);

	List<KeycloakUser> listUsers(String realm);

	KeyCloakRole getRole(String realm, String roleId);

	List<KeyCloakRole> listRoles(String realm);

	List<KeyCloakRole> getUnassignedRolesByUser(String realm, String userId);
}
