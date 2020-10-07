package com.uaa;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.uaa.entities.AppRole;
import com.uaa.rest.dto.DataRegister;
import com.uaa.service.UserService;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@RefreshScope
public class UaaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}

	// @Bean
	CommandLineRunner start(UserService userService) {
		return args -> {
			userService.save(new AppRole(null, "USER"));
			userService.save(new AppRole(null, "ADMIN"));
			Stream.of("user one user1@gmail.com", "user two user2@gmail.com", "user three user3@gmail.com",
					"admin admin admin@gmail.com", "zakaria ennajeh zakaria.ennajeh@gmail.com").forEach(un -> {
						userService.saveUser(new DataRegister(un.split(" ")[0], un.split(" ")[1], un.split(" ")[2],
								"0009384200", "123456", "123456"));
					});
			userService.addRoleToUser("admin@gmail.com", "ADMIN");
		};
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
