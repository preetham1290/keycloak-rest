package com.spaneos.user.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spaneos.keycloak.model.KeycloakUser;
import com.spaneos.user.dto.UserDTO;
import com.spaneos.user.model.MyAppUser;

@Component
public class UtilService {

	public static final Logger LOGGER = LoggerFactory.getLogger(UtilService.class);

	public KeycloakUser getKeycloakUser(UserDTO userDTO) {
		KeycloakUser keycloakUser = new KeycloakUser();
		keycloakUser.setEmail(userDTO.getEmail());
		keycloakUser.setEnabled(userDTO.isEnabled());
		keycloakUser.setKeyCloakUserId(userDTO.getKeyCloakUserId());
		keycloakUser.setPassword(userDTO.getPassword());
		keycloakUser.setRoleIds(userDTO.getRoleIds());
		keycloakUser.setTemporary(userDTO.isTemporary());
		keycloakUser.setUserName(userDTO.getUserName());
		return keycloakUser;
	}

	public MyAppUser getMyAppUser(UserDTO userDTO) {
		MyAppUser appUser = new MyAppUser();
		appUser.setId(userDTO.getId());
		appUser.setUserName(userDTO.getUserName());
		appUser.setEmail(userDTO.getEmail());
		appUser.setFirstName(userDTO.getFirstName());
		appUser.setLastName(userDTO.getLastName());
		appUser.setMobileNumber(userDTO.getMobileNumber());
		appUser.setKeyCloakUserId(userDTO.getKeyCloakUserId());
		return appUser;
	}

	public UserDTO getUserDTO(KeycloakUser keycloakUser, MyAppUser appUser) {
		UserDTO userDTO = new UserDTO();
		if (keycloakUser != null) {
			userDTO.setKeyCloakUserId(keycloakUser.getKeyCloakUserId());
			userDTO.setEmail(keycloakUser.getEmail());
			userDTO.setUserName(keycloakUser.getUserName());
			userDTO.setEnabled(keycloakUser.isEnabled());
			userDTO.setRoleIds(keycloakUser.getRoleIds());
		}
		if (appUser != null) {
			userDTO.setId(appUser.getId());
			userDTO.setFirstName(appUser.getFirstName());
			userDTO.setLastName(appUser.getLastName());
			userDTO.setMobileNumber(appUser.getMobileNumber());
			if (keycloakUser == null) {
				userDTO.setKeyCloakUserId(appUser.getKeyCloakUserId());
				userDTO.setEmail(appUser.getEmail());
				userDTO.setUserName(appUser.getUserName());
			}
		}
		return userDTO;
	}

	private static HttpServletRequest getCurrentHttpRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			return request;
		}
		return null;
	}

	public String getRealmName() {
		getCurrentHttpRequest();
		return "master";
	}
}
