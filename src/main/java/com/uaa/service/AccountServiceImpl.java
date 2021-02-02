package com.uaa.service;

import java.security.Principal;
import java.security.Security;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uaa.dao.AppRoleRepository;
import com.uaa.dao.AppUserRepository;
import com.uaa.entities.AppRole;
import com.uaa.entities.AppUser;
import com.uaa.rest.dto.UserDto;
import com.uaa.rest.mapper.UserRestMapper;
import org.springframework.util.StringUtils;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

@Service
@CommonsLog
@Transactional
public class AccountServiceImpl implements UserService {
	private AppUserRepository appUserRepository;
	private AppRoleRepository appRoleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepository = appUserRepository;
		this.appRoleRepository = appRoleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public void saveUser(UserDto userForm) {
		AppUser user = appUserRepository.findByEmail(userForm.getEmail());
		if (user != null)
			throw new RuntimeException("User already exists");

		AppUser appUser = new AppUser();
		appUser.setEmail(userForm.getEmail());
		appUser.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
		appUserRepository.save(appUser);
		addRoleToUser(userForm.getEmail(), userForm.getRole().toUpperCase());
	}

	@Override
	public UserDto updatePassword(UserDto userForm) {
		AppUser appUser = appUserRepository.findByEmail(userForm.getEmail());
		if (appUser == null)
			throw new RuntimeException("User Doesnt exists");

		appUser.setEmail(userForm.getEmail());
		appUser.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
		AppUser savedUser = appUserRepository.save(appUser);
		return UserRestMapper.convertToDto(savedUser);
	}

	@Override
	public AppRole save(AppRole role) {
		return appRoleRepository.save(role);
	}

	@Override
	public AppUser loadUserByUsername(String username) {
		return appUserRepository.findByEmail(username);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		AppUser appUser = appUserRepository.findByEmail(username);
		AppRole appRole = appRoleRepository.findByRoleName(rolename);
		appUser.getRoles().add(appRole);
	}

	@Override
	public void removeByMail(String mail) {
		AppUser userToDelete = appUserRepository.findByEmail(mail);
		appUserRepository.delete(userToDelete);
	}

	@Override
	public Map<String, String> login(HttpServletRequest request, String email) {
		AppUser user = appUserRepository.findByEmail(email);
		log.info("----login----");
		if (user != null) {
			final HttpServletRequest httpRequest = (HttpServletRequest)request;
			//extract token from header
			final String accessToken = httpRequest.getHeader("Authorization");
			//return UserRestMapper.convertToDto(user);
			log.info("login passed with token = " + accessToken);
			return Map.of("accessToken", accessToken);
		}
		return null;



	}

	@Override
	public List<String> findAll() {
		return appUserRepository.findAll()
				.stream()
				.map(user -> user.getEmail())
				.collect(Collectors.toList());
	}

}
