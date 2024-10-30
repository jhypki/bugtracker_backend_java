package com.example.bugtracker_backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilsTest {

    private final JwtUtils jwtUtils = new JwtUtils("MockSecretKeyForJwtTokenGenerationWithLengthGreaterThan256Bits");

    @Test
    void generateToken_ValidEmail_ReturnsToken() {
        // Arrange
        String email = "test@example.com";

        // Act
        String token = jwtUtils.generateToken(email);

        // Assert
        assertNotNull(token);
        assertInstanceOf(String.class, token);
        assertEquals(145, token.length());
    }

    @Test
    void extractEmail_ValidToken_ReturnsEmail() {
        // Arrange
        String email = "test@example.com";
        String token = jwtUtils.generateToken(email);

        // Act
        String extractedEmail = jwtUtils.extractEmail(token);

        // Assert
        assertNotNull(extractedEmail);
        assertEquals(email, extractedEmail);
    }

    @Test
    void extractEmail_InvalidToken_ThrowsMalformedJwtException() {
        // Arrange
        String invalidToken = "invalid.To.ken";

        // Act & Assert
        assertThrows(MalformedJwtException.class, () -> {
            jwtUtils.extractEmail(invalidToken);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", ""})
    void extractEmail_InvalidOrEmptyToken_ThrowsIllegalArgumentException(String token) {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            jwtUtils.extractEmail(token);
        });
    }

    @Test
    void extractClaim_ValidToken_ReturnsClaims() {
        // Arrange
        String email = "test@example.com";
        String token = jwtUtils.generateToken(email);

        // Act
        String claim = jwtUtils.extractClaim(token, Claims::getSubject);

        // Assert
        assertNotNull(claim);
        assertEquals(email, claim);
    }

    @Test
    void extractClaim_InvalidToken_ThrowsMalformedJwtException() {
        // Arrange
        String invalidToken = "invalid.To.ken";

        // Act & Assert
        assertThrows(MalformedJwtException.class, () -> {
            jwtUtils.extractClaim(invalidToken, Claims::getSubject);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", ""})
    void extractClaim_InvalidOrEmptyToken_ThrowsIllegalArgumentException(String token) {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            jwtUtils.extractClaim(token, Claims::getSubject);
        });
    }

    @Test
    void isTokenExpired_ValidToken_ReturnsFalse() {
        // Arrange
        String email = "test@example.com";
        String token = jwtUtils.generateToken(email);

        // Act
        boolean isExpired = jwtUtils.isTokenExpired(token);

        // Assert
        assertFalse(isExpired);
    }

    @Test
    void isTokenExpired_InvalidToken_ThrowsMalformedJwtException() {
        // Arrange
        String invalidToken = "invalid.To.ken";

        // Act & Assert
        assertThrows(MalformedJwtException.class, () -> {
            jwtUtils.isTokenExpired(invalidToken);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", ""})
    void isTokenExpired_InvalidOrEmptyToken_ThrowsIllegalArgumentException(String token) {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            jwtUtils.isTokenExpired(token);
        });
    }

    @Test
    void validateToken_ValidToken_ReturnsTrue() {
        // Arrange
        String email = "test@example.com";
        String token = jwtUtils.generateToken(email);

        // Act
        boolean isValid = jwtUtils.validateToken(token, email);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateToken_InvalidToken_ReturnsFalse() {
        // Arrange
        String email = "test@example.com";
        String token = jwtUtils.generateToken(email);

        // Act
        boolean isValid = jwtUtils.validateToken(token, "different@email.com");

        // Assert
        assertFalse(isValid);
    }

    @Test
    void validateToken_InvalidToken_ThrowsMalformedJwtException() {
        // Arrange
        String invalidToken = "invalid.To.ken";

        // Act & Assert
        assertThrows(MalformedJwtException.class, () -> {
            jwtUtils.validateToken(invalidToken, "test@example.com");
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", ""})
    void validateToken_InvalidOrEmptyToken_ThrowsIllegalArgumentException(String token) {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            jwtUtils.validateToken(token, "test@example.com");
        });
    }
}
