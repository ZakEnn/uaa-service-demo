package com.easysign.sec;

public interface SecurityParams {
	public static final String JWT_HEADER_NAME = "Authorization";
	public static final String SECRET = "&éAZERTYUI";
	public static final long EXPIRATION = 1000 * 3600 * 24;
	public static final String HEADER_PREFIX = "Bearer ";
}
