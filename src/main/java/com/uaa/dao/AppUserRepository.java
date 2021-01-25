package com.uaa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uaa.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	public AppUser findByEmail(String email);
}
