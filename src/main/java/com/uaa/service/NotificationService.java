package com.uaa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.uaa.rest.dto.NotificationDto;

@RefreshScope
@Service
public class NotificationService {
	@Value("${notification.ws}")
	private String notificationWs;

	@Autowired
	RestTemplate restTemplate;

	public NotificationDto sendNotif(String userName, NotificationDto notificationDto) {
		notificationDto.setSender(userName);
		return restTemplate.postForObject(notificationWs + "/send-notification", notificationDto,
				NotificationDto.class);
	}

}
