package com.spaneos.keycloak.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class KeycloakConfigResolverService {

	public static final Logger LOGGER = LoggerFactory.getLogger(KeycloakConfigResolverService.class);

	public Object getClientConfigJSON(String path) throws IOException {
		if (isRequestFromLocal(path)) {
			File file = getConfigFile("dev");
			LOGGER.info("getClientConfigJSON : requested from local machine and origin is : {} and returning file path is {}", path,
					file.toPath());
			String content = getAsString(file);
			return content;
		}
		String realm = getRealmName(path);
		File file = getConfigFile(realm);
		String content = getAsString(file);
		return content;
	}

	public InputStream getClientConfigIS(String path) throws FileNotFoundException {
		if (isRequestFromLocal(path)) {
			File file = getConfigFile("dev");
			LOGGER.info("getClientConfigIS : requested from local machine and origin is : {} and returning file path is {}", path,
					file.toPath());
			InputStream content = getAsStream(file);
			return content;
		}
		String realm = getRealmName(path);
		File file = getConfigFile(realm);
		InputStream content = getAsStream(file);
		return content;
	}

	private String getRealmName(String path) {
		String[] domains = path.split("\\.");
		LOGGER.info("request origin is : {} and split by . is : {}", path, domains);
		if (domains.length < 3) {
			throw new IllegalStateException("Not able to resolve realm from the request path!");
		}
		String realm = domains[0];
		return realm;
	}

	private boolean isRequestFromLocal(String path) {
		return path.indexOf("localhost") != -1;
	}

	private InputStream getAsStream(File file) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(file);
		return inputStream;
	}

	private String getAsString(File file) throws IOException {
		String content = new String(Files.readAllBytes(file.toPath()));
		return content;
	}

	private File getConfigFile(String realm) throws FileNotFoundException {
		File file = ResourceUtils.getFile("classpath:" + realm + "-keycloak.json");
		return file;
	}
}
