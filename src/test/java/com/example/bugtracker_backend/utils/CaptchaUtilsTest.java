package com.example.bugtracker_backend.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CaptchaUtilsTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CaptchaUtils captchaUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void verifyCaptcha_WhenTokenIsValid_ReturnsTrue() {
        // Arrange
        String token = "valid token";
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(
                new ResponseEntity<>(Map.of("success", true), HttpStatus.OK)
        );

        // Act
        boolean result = captchaUtils.verifyCaptcha(token);

        // Assert
        assertTrue(result);
    }

    @Test
    public void verifyCaptcha_WhenTokenIsInvalid_ReturnsFalse() {
        // Arrange
        String token = "invalid token";
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(
                new ResponseEntity<>(Map.of("success", false), HttpStatus.OK)
        );

        // Act
        boolean result = captchaUtils.verifyCaptcha(token);

        // Assert
        assertFalse(result);
    }

    @Test
    public void verifyCaptcha_WhenResponseIsNull_ReturnsFalse() {
        // Arrange
        String token = "valid token";
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(
                new ResponseEntity<>(null, null, HttpStatus.OK)
        );

        // Act
        boolean result = captchaUtils.verifyCaptcha(token);

        // Assert
        assertFalse(result);
    }
}
