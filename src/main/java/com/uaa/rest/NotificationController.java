package com.uaa.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uaa.entities.AppUser;
import com.uaa.rest.dto.NotificationDto;
import com.uaa.rest.mapper.UserRestMapper;
import com.uaa.service.AccountService;
import com.uaa.service.NotificationService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@Autowired
	AccountService accountService;

	@PostMapping("/send-notification")
	@ResponseStatus(HttpStatus.OK)
	public NotificationDto sendEmail(Principal principal, @RequestBody NotificationDto notificationDto) {
		AppUser user = accountService.loadUserByUsername(principal.getName());
		notificationDto.setSender(UserRestMapper.convertToDto(user));
		log.info("send informations : " + notificationDto);

		return notificationService.sendNotif(notificationDto);
	}
}
