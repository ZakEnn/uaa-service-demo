package com.uaa.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uaa.rest.dto.UserDto;
import com.uaa.rest.mapper.UserRestMapper;
import com.uaa.service.UserService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<UserDto> authenticate(@RequestBody UserDto userDto) {
		UserDto userAuthenticated = userService.login(userDto.getEmail());
		return ResponseEntity.status(HttpStatus.OK).body(userAuthenticated);
	}

	@PostMapping("/users/role/{role}")
	public ResponseEntity<UserDto> register(@PathVariable String role, @RequestBody UserDto userDto) {
		log.info("register user with params : {} ", userDto);
		userService.saveUser(userDto, role);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		log.info("get all users");
		List<UserDto> usersDtos = userService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(usersDtos);
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
