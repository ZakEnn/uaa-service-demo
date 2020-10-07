package com.uaa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConfigService {
	@Autowired
	RestTemplate restTemplate;

	@Value("${config.ws: http://localhost:8888}")
	private String configWs;

	public String refresh() {
		return restTemplate.postForObject(configWs + "/actuator/bus-refresh/", null, String.class);
	}

}
