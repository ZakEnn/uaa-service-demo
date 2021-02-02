package com.uaa.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenDto {
    private String accessToken;
    private String tokenType;
    private Date expiresAt;
}
