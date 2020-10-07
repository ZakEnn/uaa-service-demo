package com.uaa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uaa.service.ConfigService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RefreshScope
public class ConfigurationController {

	@Autowired
	ConfigService configService;

	@GetMapping(value = "/refresh-config")
	public @ResponseBody String refreshConfig() {
		log.info("refreshing configuration launched");
		return configService.refresh();
	}

}
