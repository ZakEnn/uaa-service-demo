package com.uaa;

import com.uaa.dao.AppRoleRepository;
import com.uaa.entities.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class UaaServiceApplication implements CommandLineRunner {

	@Autowired
	AppRoleRepository appRoleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		AtomicLong atomicLong = new AtomicLong(1);
		Arrays.asList("ADMIN", "USER", "RECRUITER")
				.stream().forEach( role ->{
					if(appRoleRepository.findByRoleName(role) == null)
						appRoleRepository.save(new AppRole(atomicLong.getAndIncrement(), role));
				}
		);
	}
}
