package com.spaneos.app.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spaneos.keycloak.service.KeycloakConfigResolverService;

@RestController
public class KeycloakController {

	@Autowired
	private KeycloakConfigResolverService service;

	@GetMapping(value = "/public/kcinit")
	public Object getKeycloakInitJson(HttpServletRequest request) throws IOException {
		String path = request.getServerName();

		Object content = service.getClientConfigJSON(path);

		return content;
	}
}
