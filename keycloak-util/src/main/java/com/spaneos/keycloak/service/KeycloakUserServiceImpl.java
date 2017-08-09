package com.spaneos.keycloak.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spaneos.keycloak.adminclient.Keycloak;
import com.spaneos.keycloak.adminclient.resource.UserResource;
import com.spaneos.keycloak.exception.KeyCloakServiceException;
import com.spaneos.keycloak.model.KeyCloakRole;
import com.spaneos.keycloak.model.KeycloakUser;

@Component
public class KeycloakUserServiceImpl implements KeycloakUserService {

	public static final Logger LOGGER = LoggerFactory.getLogger(KeycloakUserServiceImpl.class);

	@Autowired
	private Keycloak keycloak;

	@Override
	public String create(KeycloakUser keycloakUser, String realm) {
		UserRepresentation user = getUserRepresentation(keycloakUser);
		Response result = keycloak.realm(realm).users().create(user);
		if (result.getStatus() != 201) {
			LOGGER.info("Unable to create user. Keycloak responded with status {}", result.getStatus());
			throw new KeyCloakServiceException(
					"Unable to create user. Keycloak responded with status " + result.getStatus());
		}
		String userId = getCreatedUserId(result);
		keycloakUser.setKeyCloakUserId(userId);
		updatePassword(keycloakUser, realm);
		updateRoles(keycloakUser, realm);
		return userId;
	}

	@Override
	public void updatePassword(KeycloakUser keycloakUser, String realm) {
		UserResource userResource = getUserResourceByUserId(realm, keycloakUser.getKeyCloakUserId());
		CredentialRepresentation credential = getCredentials(keycloakUser);
		userResource.resetPassword(credential);
	}

	@Override
	public void update(KeycloakUser keycloakUser, String realm) {
		UserResource userResource = getUserResourceByUserId(realm, keycloakUser.getKeyCloakUserId());
		UserRepresentation representation = userResource.toRepresentation();
		representation.setEnabled(keycloakUser.isEnabled());
		updateRoles(keycloakUser, realm);
		userResource.update(representation);
	}

	@Override
	public KeycloakUser getUser(String realm, String userId) {
		UserResource userResource = getUserResourceByUserId(realm, userId);
		KeycloakUser keycloakUser = getKeyCloakUser(userResource, realm);
		return keycloakUser;
	}

	@Override
	public List<KeycloakUser> listUsers(String realm) {
		List<UserRepresentation> users = keycloak.realm(realm).users().search("", 0, 100);
		List<KeycloakUser> keycloakUsers = new ArrayList<>();
		users.stream().forEach(user -> {
			UserResource userResource = getUserResourceByUserId(realm, user.getId());
			keycloakUsers.add(getKeyCloakUser(userResource, realm));
		});
		return keycloakUsers;
	}

	@Override
	public List<KeyCloakRole> getUnassignedRolesByUser(String realm, String userId) {
		UserResource userResource = getUserResourceByUserId(realm, userId);
		List<RoleRepresentation> list = userResource.roles().realmLevel().listAvailable();
		List<KeyCloakRole> roles = convert(list);
		return roles;
	}

	@Override
	public KeyCloakRole getRole(String realm, String roleId) {
		RoleRepresentation representation = keycloak.realm(realm).rolesById().getRole(roleId);
		KeyCloakRole cloakRole = convert(representation);
		return cloakRole;
	}

	@Override
	public List<KeyCloakRole> listRoles(String realm) {
		List<RoleRepresentation> list = keycloak.realm(realm).roles().list();
		List<KeyCloakRole> roles = convert(list);
		return roles;
	}

	private List<KeyCloakRole> convert(List<RoleRepresentation> list) {
		List<KeyCloakRole> roles = new ArrayList<>();
		list.stream().forEach(r -> {
			roles.add(convert(r));
		});
		return roles;
	}

	private KeyCloakRole convert(RoleRepresentation role) {
		KeyCloakRole cloakRole = new KeyCloakRole();
		cloakRole.setId(role.getId());
		cloakRole.setName(role.getName());
		return cloakRole;
	}

	private List<RoleRepresentation> getUnassignedRolesByUserId(String realm, String userId) {
		UserResource userResource = getUserResourceByUserId(realm, userId);
		List<RoleRepresentation> list = userResource.roles().realmLevel().listAvailable();
		return list;
	}

	private void updateRoles(KeycloakUser keycloakUser, String realm) {
		List<RoleRepresentation> rolesToAssign = getRolesToAssign(keycloakUser, realm);
		List<RoleRepresentation> rolesToRemove = getRolesToRemove(keycloakUser, realm);
		UserResource userResource = getUserResourceByUserId(realm, keycloakUser.getKeyCloakUserId());
		userResource.roles().realmLevel().add(rolesToAssign);
		userResource.roles().realmLevel().remove(rolesToRemove);
	}

	private List<RoleRepresentation> getRolesToAssign(KeycloakUser keycloakUser, String realm) {
		List<RoleRepresentation> unassignedRoles = getUnassignedRolesByUserId(realm, keycloakUser.getKeyCloakUserId());
		List<String> roleIds = keycloakUser.getRoleIds();
		List<RoleRepresentation> rolesToAssign = unassignedRoles.stream().filter(role -> {
			return roleIds.contains(role.getId());
		}).collect(Collectors.toList());
		return rolesToAssign;
	}

	private List<RoleRepresentation> getRolesToRemove(KeycloakUser keycloakUser, String realm) {
		List<RoleRepresentation> assignedRoles = getAssignedRoleByUser(realm, keycloakUser.getKeyCloakUserId());
		List<String> defaultRoles = keycloak.realm(realm).toRepresentation().getDefaultRoles();
		List<String> roleIds = keycloakUser.getRoleIds();
		List<RoleRepresentation> rolesToRemove = assignedRoles.stream().filter(role -> {
			return !roleIds.contains(role.getId()) && !defaultRoles.contains(role.getName());
		}).collect(Collectors.toList());
		return rolesToRemove;
	}

	private UserResource getUserResourceByUserId(String realm, String userId) {
		return keycloak.realm(realm).users().get(userId);
	}

	private UserRepresentation getUserRepresentation(KeycloakUser keycloakUser) {
		UserRepresentation user = new UserRepresentation();
		user.setUsername(keycloakUser.getUserName());
		user.setEmail(keycloakUser.getEmail());
		user.setEnabled(keycloakUser.isEnabled());
		return user;
	}

	private CredentialRepresentation getCredentials(KeycloakUser keycloakUser) {
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(keycloakUser.getPassword());
		credential.setTemporary(keycloakUser.isTemporary());
		return credential;
	}

	private String getCreatedUserId(Response response) {
		String locationHeader = response.getHeaderString("Location");
		String userId = locationHeader.replaceAll(".*/(.*)$", "$1");
		return userId;
	}

	private KeycloakUser getKeyCloakUser(UserResource resource, String realm) {
		UserRepresentation representation = resource.toRepresentation();
		KeycloakUser keycloakUser = new KeycloakUser();
		keycloakUser.setEmail(representation.getEmail());
		keycloakUser.setEnabled(representation.isEnabled());
		keycloakUser.setKeyCloakUserId(representation.getId());
		keycloakUser.setPassword(null);
		List<RoleRepresentation> assignedRoles = getAssignedRoleByUser(realm, representation.getId());
		assignedRoles.stream().forEach(role -> {
			keycloakUser.getRoleIds().add(role.getId());
		});
		keycloakUser.setTemporary(false);
		keycloakUser.setUserName(representation.getUsername());
		return keycloakUser;
	}

	private List<RoleRepresentation> getAssignedRoleByUser(String realm, String userId) {
		UserResource userResource = getUserResourceByUserId(realm, userId);
		List<RoleRepresentation> assignedRoles = userResource.roles().realmLevel().listEffective();
		return assignedRoles;
	}
}