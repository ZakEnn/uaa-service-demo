package com.uaa.service;

import java.util.Map;

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

	private HttpHeaders headers;
	private RestTemplate restTemplate = new RestTemplate();

	public void initHeader() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	public String sendNotif(Map<String, String> data) {
		initHeader();

		String url = notificationWs + "/send-mail";

		HttpEntity request = new HttpEntity<>(data, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		return response.getBody();

	}

}
