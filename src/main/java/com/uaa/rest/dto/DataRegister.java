package com.uaa.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRegister {
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String password;
	private String confirmedPassword;
}
