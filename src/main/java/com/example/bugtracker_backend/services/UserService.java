package com.example.bugtracker_backend.services;

import com.example.bugtracker_backend.dto.AuthenticationResponse;
import com.example.bugtracker_backend.dto.LoginRequest;
import com.example.bugtracker_backend.dto.RegisterRequest;
import com.example.bugtracker_backend.dto.UserData;
import com.example.bugtracker_backend.exceptions.BadRequestException;
import com.example.bugtracker_backend.exceptions.ConflictException;
import com.example.bugtracker_backend.exceptions.NotFoundException;
import com.example.bugtracker_backend.mappers.UserDataMapper;
import com.example.bugtracker_backend.models.User;
import com.example.bugtracker_backend.models.UsersRole;
import com.example.bugtracker_backend.repositories.UserRepository;
import com.example.bugtracker_backend.utils.CaptchaUtils;
import com.example.bugtracker_backend.utils.EnumUtils;
import com.example.bugtracker_backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user-related operations, such as registration,
 * authentication,
 * user retrieval, and role updates.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CaptchaUtils captchaUtils;

    @Value("${enable.captcha:true}")
    private Boolean isCaptchaEnabled;

    /**
     * Constructor for UserService.
     *
     * @param userRepository  the user repository
     * @param passwordEncoder the password encoder
     * @param jwtUtils        the JWT utility
     * @param captchaUtils    the captcha utility
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils,
            CaptchaUtils captchaUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.captchaUtils = captchaUtils;
    }

    /**
     * Registers a new user account.
     *
     * @param registerRequest the registration request containing user details and
     *                        captcha token
     * @return an authentication response with a JWT token and user data
     * @throws ConflictException if the email is already in use
     */
    public AuthenticationResponse registerUserAccount(RegisterRequest registerRequest) {
        validateCaptchaToken(registerRequest.getCaptchaToken());

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException("Email is already in use");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setSecondName(registerRequest.getLastName());

        User savedUser = userRepository.save(user);

        UserData userData = UserDataMapper.toUserData(savedUser);

        String token = jwtUtils.generateToken(user.getEmail());

        return new AuthenticationResponse(token, userData);
    }

    /**
     * Authenticates a user using email and password.
     *
     * @param loginRequest the login request containing email, password, and captcha
     *                     token
     * @return an authentication response with a JWT token and user data
     * @throws BadRequestException if the user is not found or the password is
     *                             invalid
     */
    public AuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        validateCaptchaToken(loginRequest.getCaptchaToken());

        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            throw new BadRequestException("User not found");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Invalid password");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return new AuthenticationResponse(token, UserDataMapper.toUserData(user));
    }

    /**
     * Retrieves all users.
     *
     * @return a list of user data
     * @throws BadRequestException if no users are found
     */
    public List<UserData> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new BadRequestException("No users found");
        }

        return users.stream().map(UserDataMapper::toUserData).toList();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the user ID
     * @return the user data
     * @throws NotFoundException if the user is not found
     */
    public UserData getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        checkIfUserExists(user.orElse(null));

        return UserDataMapper.toUserData(user.get());
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the user email
     * @return the user data
     * @throws NotFoundException if the user is not found
     */
    public UserData getUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        checkIfUserExists(user.orElse(null));

        return UserDataMapper.toUserData(user.get());
    }

    /**
     * Updates the role of a user.
     *
     * @param id   the user ID
     * @param role the new role
     * @return the updated user data
     * @throws BadRequestException if the user is not found or the role is invalid
     */
    public UserData updateUserRole(Integer id, String role) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        if (!EnumUtils.isValidEnum(UsersRole.class, role)) {
            throw new BadRequestException("Invalid role");
        }

        user.get().setRole(UsersRole.valueOf(role));
        User updatedUser = userRepository.save(user.get());

        return UserDataMapper.toUserData(updatedUser);
    }

    /**
     * Validates the provided captcha token if captcha validation is enabled.
     *
     * @param captchaToken the captcha token to validate
     * @throws BadRequestException if the captcha token is invalid
     */
    private void validateCaptchaToken(String captchaToken) {
        if (!isCaptchaEnabled) {
            return;
        }
        if (!captchaUtils.verifyCaptcha(captchaToken)) {
            throw new BadRequestException("Invalid captcha token");
        }
    }

    /**
     * Checks if a user exists.
     *
     * @param user the user to check
     * @throws NotFoundException if the user is null
     */
    private void checkIfUserExists(User user) {
        if (user == null) {
            throw new NotFoundException("User not found");
        }
    }
}
