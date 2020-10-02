package com.uaa.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@Service
public class NotificationService {
	@Value("${notification.ws}")
	private String notificationWs;

	@Autowired
	RestTemplate restTemplate;

	private HttpHeaders headers;

	public void initHeader() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	public String sendNotif(Map<String, Object> dataObject) {
		initHeader();

		String url = notificationWs + "/send-notification";

		HttpEntity request = new HttpEntity<>(dataObject, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		return response.getBody();

	}

}
