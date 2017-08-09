package com.spaneos.app.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

	@GetMapping(value = "/test")
	public String testApi() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return "MultiTenant demo woring " + auth.getName();
	}
}
