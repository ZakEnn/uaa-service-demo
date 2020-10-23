package com.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@RefreshScope
public class UaaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

}
