package com.spaneos.user.service;

import java.util.List;

import com.spaneos.keycloak.model.KeyCloakRole;
import com.spaneos.user.dto.UserDTO;

public interface MyAppUserService {

	public UserDTO create(UserDTO userDTO);

	public UserDTO update(UserDTO userDTO);

	public UserDTO updatePassword(UserDTO userDTO);

	public UserDTO getUser(String userId);

	public List<UserDTO> getAll();

	public KeyCloakRole getRole(String roleId);

	public List<KeyCloakRole> getAllRoles();

	public List<KeyCloakRole> getAssignedRole(String userId);
}
