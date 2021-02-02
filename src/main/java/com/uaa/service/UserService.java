package com.uaa.service;

import java.util.List;
import java.util.Map;

import com.uaa.entities.AppRole;
import com.uaa.entities.AppUser;
import com.uaa.rest.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
	public void saveUser(UserDto userDto);

	public UserDto updatePassword(UserDto userDto);

	public AppRole save(AppRole role);

	public AppUser loadUserByUsername(String username);

	public void addRoleToUser(String username, String rolename);

	public void removeByMail(String mail);

	public Map<String,String> login(HttpServletRequest request, String email);

	public List<String> findAll();

}
