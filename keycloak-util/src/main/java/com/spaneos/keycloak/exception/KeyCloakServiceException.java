package com.spaneos.keycloak.exception;

public class KeyCloakServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8258045846964591100L;

	private String errorMessage;

	public KeyCloakServiceException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public KeyCloakServiceException() {

		this.errorMessage = "No account number found!";
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}