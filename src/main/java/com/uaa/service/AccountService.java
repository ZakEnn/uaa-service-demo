package com.uaa.service;

import java.util.List;

import com.uaa.entities.AppRole;
import com.uaa.entities.AppUser;
import com.uaa.rest.dto.UserDto;

public interface AccountService {
	public AppUser saveUser(DataRegister userForm);

	public AppUser updateUser(String mail, DataRegister userForm);

	public AppRole save(AppRole role);

	public AppUser loadUserByUsername(String username);

	public void addRoleToUser(String username, String rolename);

	public void removeByMail(String mail);

	public UserDto login(String email);

	public List<UserDto> findAll();

}
