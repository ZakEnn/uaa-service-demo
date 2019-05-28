package com.easysign;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.easysign.entities.AppRole;
import com.easysign.service.AccountService;
import com.easysign.service.DataRegister;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@RefreshScope
public class UaaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}

	// @Bean
	CommandLineRunner start(AccountService accountService) {
		return args -> {
			accountService.save(new AppRole(null, "USER"));
			accountService.save(new AppRole(null, "ADMIN"));
			Stream.of("user one user1@gmail.com", "user two user2@gmail.com", "user three user3@gmail.com",
					"admin admin admin@gmail.com").forEach(un -> {
						accountService.saveUser(new DataRegister(un.split(" ")[0], un.split(" ")[1], un.split(" ")[2],
								"0009384200", "123456", "123456"));
					});
			accountService.addRoleToUser("admin@gmail.com", "ADMIN");
		};
	}

	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

}
