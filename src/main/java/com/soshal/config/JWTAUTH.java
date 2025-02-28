package com.soshal.config;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTAUTH {
   // public static final String SECRET_KEY = System.getenv("JWT_SECRET") != null
           // ? System.getenv("JWT_SECRET")
           // : "default_secret_key_for_dev_only";
    // Use Base64-encoded key for security
   public static final String SECRET_KEY = System.getenv("JWT_SECRET") != null
           ? System.getenv("JWT_SECRET")
           : Base64.getEncoder().encodeToString("default_secret_key_for_dev_only".getBytes(StandardCharsets.UTF_8));

    public static final String JWT_HEADER = "Authorization"; // Fix incorrect header key

    public static String getSecretKey() {
        return SECRET_KEY;
    }
}
