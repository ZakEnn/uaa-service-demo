package com.uaa.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@RefreshScope
@Service
@Log4j2
public class GaiaService {
	@Value("${gaia.path}")
	private String gaiaPath;

	@Value("${gaia.token}")
	private String gaiaToken;

	private HttpHeaders headers;
	private RestTemplate restTemplate = new RestTemplate();

	public void initHeader() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("xapp_token", gaiaToken);
	}

	public String signNotif(Map<String, String> model) {
		initHeader();

		String url = gaiaPath + "/transactions?inputInline=true";

		HttpEntity request = new HttpEntity<>(model, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		return response.getBody();

	}

	public Map signingProcess(String transactionId, Map<String, String> model) {
		initHeader();

		String url = gaiaPath + "/transactions/" + transactionId + "/tasks?inputInline=true";

		HttpEntity request = new HttpEntity<>(model, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
		if (response.getStatusCode().is5xxServerError()) {
			log.warn("Server Error 500, infos : signing process is not working");
		}
		return response.getBody();

	}

	public Map getTasksByUser(String mail) {
		initHeader();

		String url = this.gaiaPath + "/tasks/assignee/" + mail;

		HttpEntity requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
		if (responseEntity.getStatusCode().is5xxServerError()) {
			log.warn("Server Error 500, infos : fetching tasks from user with mail [{}] not working", mail);
		}
		return responseEntity.getBody();

	}

	public Map getTaskForm(String transactionId, String taskName) {
		initHeader();

		String url = gaiaPath + "/transactions/" + transactionId + "/tasks/" + taskName + "?outputInline=true";

		HttpEntity requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);

		return responseEntity.getBody();
	}

}
