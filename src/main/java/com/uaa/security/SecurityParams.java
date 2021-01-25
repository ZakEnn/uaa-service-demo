package com.uaa.security;

public final class SecurityParams {
	private SecurityParams() {
		throw new IllegalAccessError("Utility Constants class");
	}

	public static final String JWT_HEADER_NAME = "Authorization";
	public static final String SECRET = "&éAZERTYUI";
	public static final long EXPIRATION = 1000 * 3600 * 24;
	public static final String HEADER_PREFIX = "Bearer ";
}
