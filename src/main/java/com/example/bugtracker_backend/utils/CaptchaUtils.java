package com.example.bugtracker_backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Utility class for verifying Google reCAPTCHA tokens.
 * This class uses a secret key and a RestTemplate to communicate with the
 * Google reCAPTCHA API.
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * {@code
 * CaptchaUtils captchaUtils = new CaptchaUtils(secretKey, restTemplate);
 * boolean isValid = captchaUtils.verifyCaptcha(token);
 * }
 * </pre>
 * 
 * <p>
 * Configuration:
 * </p>
 * <ul>
 * <li>google.recaptcha.secret: The secret key for Google reCAPTCHA.</li>
 * </ul>
 * 
 * <p>
 * Dependencies:
 * </p>
 * <ul>
 * <li>RestTemplate: Used to make HTTP requests to the Google reCAPTCHA
 * API.</li>
 * </ul>
 * 
 * @author Your Name
 */
@Component
public class CaptchaUtils {
    private final String SECRET_KEY;
    private final RestTemplate restTemplate;

    public CaptchaUtils(@Value("${google.recaptcha.secret}") String secret, RestTemplate restTemplate) {
        this.SECRET_KEY = secret;
        this.restTemplate = restTemplate;
    }

    public boolean verifyCaptcha(String token) {
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", SECRET_KEY);
        requestMap.add("response", token);

        String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

        var request = new HttpEntity<>(requestMap);

        try {
            var response = restTemplate.postForEntity(SITE_VERIFY_URL, request, Map.class);
            var body = response.getBody();

            if (body != null && body.containsKey("success")) {
                return (Boolean) body.get("success");
            }
        } catch (Exception e) {
            System.err.println("Error verifying reCAPTCHA: " + e.getMessage());
        }

        return false;
    }

}
