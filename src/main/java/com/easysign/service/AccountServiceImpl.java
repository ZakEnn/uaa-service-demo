package com.easysign.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easysign.dao.AppRoleRepository;
import com.easysign.dao.AppUserRepository;
import com.easysign.entities.AppRole;
import com.easysign.entities.AppUser;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
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
	public AppUser saveUser(DataRegister userForm) {
		AppUser user = appUserRepository.findByEmail(userForm.getEmail());
		if (user != null)
			throw new RuntimeException("User already exists");
		if (!userForm.getPassword().equals(userForm.getConfirmedPassword()))
			throw new RuntimeException("Please confirm your password");
		AppUser appUser = new AppUser();
		appUser.setFirstname(userForm.getFirstname());
		appUser.setLastname(userForm.getLastname());
		appUser.setEmail(userForm.getEmail());
		appUser.setPhone(userForm.getPhone());
		appUser.setActived(true);
		appUser.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
		appUserRepository.save(appUser);
		addRoleToUser(userForm.getEmail(), "USER");
		return appUser;
	}

	@Override
	public AppUser updateUser(String mail, DataRegister userForm) {
		AppUser appUser = appUserRepository.findByEmail(mail);
		if (appUser == null)
			throw new RuntimeException("User Doesnt exists");
		if (!userForm.getPassword().equals(userForm.getConfirmedPassword()))
			throw new RuntimeException("Please confirm your password");

		appUser.setFirstname(userForm.getFirstname());
		appUser.setLastname(userForm.getLastname());
		appUser.setEmail(userForm.getEmail());
		appUser.setPhone(userForm.getPhone());
		appUser.setActived(true);
		appUser.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
		appUserRepository.save(appUser);
		return appUser;
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

}
