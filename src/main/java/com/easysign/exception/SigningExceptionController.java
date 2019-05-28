package com.easysign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.auth0.jwt.exceptions.TokenExpiredException;

@ControllerAdvice
public class SigningExceptionController {

	@ExceptionHandler({ HttpServerErrorException.class, HttpClientErrorException.class })
	public ResponseEntity exception(HttpStatusCodeException exception) {

		return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsString());
	}

	@ExceptionHandler({ TokenExpiredException.class })
	public ResponseEntity TokenExpiredException(TokenExpiredException exception) {

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
	}

}
