package com.uaa.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uaa.rest.dto.NotificationDto;
import com.uaa.service.UserService;
import com.uaa.service.NotificationService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@PostMapping("/send-notification")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<NotificationDto> sendEmail(Principal principal,
			@RequestBody NotificationDto notificationDto) {
		log.info("send informations : " + notificationDto);
		NotificationDto notificationSended = notificationService.sendNotif(principal.getName(), notificationDto);
		return ResponseEntity.status(HttpStatus.OK).body(notificationSended);

	}
}
