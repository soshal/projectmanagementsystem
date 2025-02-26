package com.soshal.config;



public class JWTAUTH {
    public static final String SECRET_KEY = System.getenv("JWT_SECRET") != null
            ? System.getenv("JWT_SECRET")
            : "default_secret_key_for_dev_only"; // Use Base64-encoded key for security

    public static final String JWT_HEADER = "Authorization"; // Fix incorrect header key

    public static String getSecretKey() {
        return SECRET_KEY;
    }
}
