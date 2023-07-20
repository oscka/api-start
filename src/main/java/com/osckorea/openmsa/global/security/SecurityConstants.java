package com.osckorea.openmsa.global.security;

public class SecurityConstants {

    public static final String JWT_SECRET = "1e47d80ac0698f4cf303c6e7ba9a64a07767a21cb3674336489a7d96dee4762ee11e6827a04fa4c172174e88063f085f1d3c9370ead4f3aab44d30b31ea7bd05"; // 64bit

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_TYPE = "JWT";

    public static final String TOKEN_ISSUER = "http://auth-service";

    public static final String TOKEN_AUDIENCE = "rest-app";

    public static final String AUTH_LOGIN_URL = "/auth";
}
