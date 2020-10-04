package com.uaa.rest.dto;

import java.util.List;

import lombok.Data;

@Data
public class NotificationDto {
	private UserDto sender;
	private List<String> receivers;
	private String object;
	private String message;
}
