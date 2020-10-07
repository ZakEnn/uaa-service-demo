package com.uaa.service;

import java.util.List;

import com.uaa.entities.AppRole;
import com.uaa.entities.AppUser;
import com.uaa.rest.dto.DataRegister;
import com.uaa.rest.dto.UserDto;

public interface UserService {
	public UserDto saveUser(DataRegister userForm);

	public UserDto updateUser(String mail, DataRegister userForm);

	public AppRole save(AppRole role);

	public AppUser loadUserByUsername(String username);

	public void addRoleToUser(String username, String rolename);

	public void removeByMail(String mail);

	public UserDto login(String email);

	public List<UserDto> findAll();

}
