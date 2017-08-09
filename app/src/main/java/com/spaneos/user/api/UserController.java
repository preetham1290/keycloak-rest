package com.spaneos.user.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spaneos.keycloak.model.KeyCloakRole;
import com.spaneos.user.dto.UserDTO;
import com.spaneos.user.service.MyAppUserService;

@RestController
public class UserController {

	@Autowired
	private MyAppUserService appUserService;

	@PostMapping(value = "/public/user")
	public UserDTO create(@RequestBody UserDTO userDTO) {
		return appUserService.create(userDTO);
	}

	@PutMapping(value = "/public/user")
	public UserDTO update(@RequestBody UserDTO userDTO) {
		return appUserService.update(userDTO);
	}

	@PutMapping(value = "/public/user/updatepassword")
	public UserDTO updatePassword(@RequestBody UserDTO userDTO) {
		return appUserService.updatePassword(userDTO);
	}

	@GetMapping(value = "/public/user/{userId}")
	public UserDTO getUser(@PathVariable String userId) {
		return appUserService.getUser(userId);
	}

	@GetMapping(value = "/public/users")
	public List<UserDTO> getAll() {
		return appUserService.getAll();
	}

	@GetMapping(value = "/public/role/{roleId}")
	public KeyCloakRole getRole(@PathVariable String roleId) {
		return appUserService.getRole(roleId);
	}

	@GetMapping(value = "/public/roles")
	public List<KeyCloakRole> getAllRoles() {
		return appUserService.getAllRoles();
	}

	@GetMapping(value = "/public/roles/unassigned/{userId}")
	public List<KeyCloakRole> getAssignedRole(@PathVariable String userId) {
		return appUserService.getAssignedRole(userId);
	}
}
