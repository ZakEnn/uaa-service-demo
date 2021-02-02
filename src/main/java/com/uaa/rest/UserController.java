package com.uaa.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uaa.rest.dto.UserDto;
import com.uaa.rest.mapper.UserRestMapper;
import com.uaa.service.UserService;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;

@RestController
@Log4j2
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> authenticate(@RequestBody UserDto userDto, HttpServletRequest request) {
		Map<String,String> token = userService.login(request, userDto.getEmail());
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
		log.info("register user with params : {} ", userDto);
		userService.saveUser(userDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/users")
	public ResponseEntity<List<String>> getAllUsers() {
		log.info("get all users");
		List<String> users = userService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping("/users/{mail}")
	public ResponseEntity<UserDto> getUserByMail(@PathVariable String mail) {
		log.info("get user with mail : {}", mail);
		UserDto userDto = UserRestMapper.convertToDto(userService.loadUserByUsername(mail));
		return ResponseEntity.status(HttpStatus.OK).body(userDto);

	}

	@DeleteMapping("/users/{mail}")
	public ResponseEntity<String> removeUser(@PathVariable String mail) {
		log.info("remove user with mail : {} ", mail);
		userService.removeByMail(mail);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping("/users")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userForm) {
		log.info("update password [{}] with params : {}", userForm);
		UserDto userDto = userService.updatePassword(userForm);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}

}
