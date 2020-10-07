package com.uaa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.auth0.jwt.exceptions.TokenExpiredException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler({ HttpServerErrorException.class, HttpClientErrorException.class })
	public ResponseEntity<String> exception(HttpStatusCodeException exception) {
		return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsString());
	}

	@ExceptionHandler({ TokenExpiredException.class })
	public ResponseEntity<String> tokenExpiredException(TokenExpiredException exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
	}

}
