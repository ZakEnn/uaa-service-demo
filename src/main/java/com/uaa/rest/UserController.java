package com.uaa.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uaa.rest.dto.DataRegister;
import com.uaa.rest.dto.UserDto;
import com.uaa.rest.mapper.UserRestMapper;
import com.uaa.service.UserService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RefreshScope
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		log.info("get all users");
		List<UserDto> usersDtos = userService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(usersDtos);
	}

	@GetMapping("/user/{mail}")
	public ResponseEntity<UserDto> getUserByMail(@PathVariable String mail) {
		log.info("get user with mail : {}", mail);
		UserDto userDto = UserRestMapper.convertToDto(userService.loadUserByUsername(mail));
		return ResponseEntity.status(HttpStatus.OK).body(userDto);

	}

	@GetMapping("/remove-user/{mail}")
	public ResponseEntity<String> removeUser(@PathVariable String mail) {
		log.info("remove user with mail : {} ", mail);
		userService.removeByMail(mail);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody DataRegister userForm) {
		log.info("register user with params : {} ", userForm);
		UserDto userDto = userService.saveUser(userForm);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);

	}

	@PostMapping("/update-user/{mail}")
	public ResponseEntity<UserDto> updateUser(@PathVariable String mail, @RequestBody DataRegister userForm) {
		log.info("update user [{}] with params : {}", mail, userForm);
		UserDto userDto = userService.updateUser(mail, userForm);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);

	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> authenticate(@RequestBody UserDto userDto) {
		UserDto userAuthenticated = userService.login(userDto.getEmail());
		return ResponseEntity.status(HttpStatus.OK).body(userAuthenticated);

	}

}
