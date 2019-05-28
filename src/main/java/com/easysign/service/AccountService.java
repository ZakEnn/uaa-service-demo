package com.easysign.service;

import com.easysign.entities.AppRole;
import com.easysign.entities.AppUser;

public interface AccountService {
	public AppUser saveUser(DataRegister userForm);

	public AppUser updateUser(String mail, DataRegister userForm);

	public AppRole save(AppRole role);

	public AppUser loadUserByUsername(String username);

	public void addRoleToUser(String username, String rolename);

}
