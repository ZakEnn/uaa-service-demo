package com.uaa.web;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uaa.entities.AppUser;
import com.uaa.service.AccountService;
import com.uaa.service.NotificationService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@Autowired
	AccountService accountService;

	@PostMapping("/send-notification")
	@ResponseStatus(HttpStatus.OK)
	public String loadData(Principal principal, @RequestBody Map<String, Object> dataObject) {
		log.info("user principal name : " + principal.getName());
		AppUser user = accountService.loadUserByUsername(principal.getName());
		// add user details
		dataObject.put("firstName", user.getFirstname());
		dataObject.put("lastName", user.getLastname());

		log.info("send informations : " + dataObject);

		return notificationService.sendNotif(dataObject);
	}
}
