package com.uaa.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.uaa.dao.AppUserRepository;
import com.uaa.entities.AppUser;
import com.uaa.rest.dto.UserDto;
import com.uaa.service.AccountService;
import com.uaa.service.DataRegister;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RefreshScope
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private AppUserRepository appUserRepository;

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/users")
	public List<UserDto> getAllUsers() {
		log.info("get users list");
		return accountService.findAll();
	}

	@GetMapping("/user/{mail}")
	public AppUser getUserByMail(@PathVariable String mail) {
		log.info("get user with mail : {}", mail);
		return appUserRepository.findByEmail(mail);
	}

	@GetMapping("/remove-user/{mail}")
	public @ResponseBody void removeUser(@PathVariable String mail) {
		log.info("remove user with mail : {} ", mail);
		accountService.removeByMail(mail);
	}

	@PostMapping("/register")
	public void register(@RequestBody DataRegister userForm) {
		log.info("register user with params : {} ", userForm);
		accountService.saveUser(userForm);
	}

	@PostMapping("/update-user/{mail}")
	public void updateUser(@PathVariable String mail, @RequestBody DataRegister userForm) {
		log.info("update user [{}] with params : {}", mail, userForm);
		accountService.updateUser(mail, userForm);
	}

	@PostMapping("/login")
	public UserDto authenticate(@RequestBody UserDto userDto) {
		return accountService.login(userDto.getEmail());
	}

}
