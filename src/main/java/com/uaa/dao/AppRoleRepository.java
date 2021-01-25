package com.uaa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uaa.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
	public AppRole findByRoleName(String roleName);
}
