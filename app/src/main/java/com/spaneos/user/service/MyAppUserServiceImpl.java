package com.spaneos.user.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spaneos.keycloak.model.KeyCloakRole;
import com.spaneos.keycloak.model.KeycloakUser;
import com.spaneos.keycloak.service.KeycloakUserService;
import com.spaneos.user.dto.UserDTO;
import com.spaneos.user.model.MyAppUser;
import com.spaneos.user.repo.MyAppUserRepo;

@Service
public class MyAppUserServiceImpl implements MyAppUserService {

	public static final Logger LOGGER = LoggerFactory.getLogger(MyAppUserServiceImpl.class);

	@Autowired
	private KeycloakUserService keycloakUserService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private MyAppUserRepo userRepo;

	@Override
	public UserDTO create(UserDTO userDTO) {

		KeycloakUser keycloakUser = utilService.getKeycloakUser(userDTO);
		MyAppUser myAppUser = utilService.getMyAppUser(userDTO);

		String id = keycloakUserService.create(keycloakUser, utilService.getRealmName());
		myAppUser.setKeyCloakUserId(id);

		myAppUser = userRepo.save(myAppUser);
		userDTO = utilService.getUserDTO(null, myAppUser);
		return userDTO;
	}

	@Override
	public List<UserDTO> getAll() {
		List<MyAppUser> appUsers = userRepo.findAll();
		List<UserDTO> list = new ArrayList<>();
		appUsers.stream().forEach(appUser -> {
			list.add(utilService.getUserDTO(null, appUser));
		});
		return list;
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		KeycloakUser keycloakUser = utilService.getKeycloakUser(userDTO);
		MyAppUser myAppUser = utilService.getMyAppUser(userDTO);

		keycloakUserService.update(keycloakUser, utilService.getRealmName());
		myAppUser = userRepo.save(myAppUser);
		userDTO = utilService.getUserDTO(null, myAppUser);
		return userDTO;
	}

	@Override
	public UserDTO updatePassword(UserDTO userDTO) {
		KeycloakUser keycloakUser = utilService.getKeycloakUser(userDTO);
		keycloakUserService.updatePassword(keycloakUser, utilService.getRealmName());
		return userDTO;
	}

	@Override
	public UserDTO getUser(String userId) {
		MyAppUser myAppUser = userRepo.findOne(userId);
		KeycloakUser keycloakUser = keycloakUserService.getUser(utilService.getRealmName(),
				myAppUser.getKeyCloakUserId());
		LOGGER.info("finding user for userId : {} and user is : ", userId, myAppUser);
		return utilService.getUserDTO(keycloakUser, myAppUser);
	}

	@Override
	public KeyCloakRole getRole(String roleId) {
		return keycloakUserService.getRole(utilService.getRealmName(), roleId);
	}

	@Override
	public List<KeyCloakRole> getAllRoles() {
		return keycloakUserService.listRoles(utilService.getRealmName());
	}

	@Override
	public List<KeyCloakRole> getAssignedRole(String userId) {
		MyAppUser myAppUser = userRepo.findOne(userId);
		return keycloakUserService.getUnassignedRolesByUser(utilService.getRealmName(), myAppUser.getKeyCloakUserId());
	}

}
