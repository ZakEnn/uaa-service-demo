package com.uaa.rest.mapper;

import org.modelmapper.ModelMapper;

import com.uaa.entities.AppUser;
import com.uaa.rest.dto.UserDto;

public final class UserRestMapper {

	private static final ModelMapper modelMapper = new ModelMapper();

	private UserRestMapper() {
		throw new UnsupportedOperationException("UserRestMapper is an utility class, can't be instantiated");
	}

	public static UserDto convertToDto(AppUser user) {
		return modelMapper.map(user, UserDto.class);
	}
}
