package com.example.bugtracker_backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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
        var response = restTemplate.postForEntity(SITE_VERIFY_URL, new HttpEntity<>(requestMap), Map.class);
        var body = response.getBody();

        return body != null && (Boolean) body.get("success");
    }
}
