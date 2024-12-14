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

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CaptchaUtils captchaUtils;

    @Value("${enable.captcha:true}")
    private Boolean isCaptchaEnabled;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, CaptchaUtils captchaUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.captchaUtils = captchaUtils;
    }

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

    public List<UserData> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new BadRequestException("No users found");
        }

        return users.stream().map(UserDataMapper::toUserData).toList();
    }

    public UserData getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        checkIfUserExists(user.orElse(null));

        return UserDataMapper.toUserData(user.get());
    }

    public UserData getUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        checkIfUserExists(user.orElse(null));

        return UserDataMapper.toUserData(user.get());
    }

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

    private void validateCaptchaToken(String captchaToken) {
        if (!isCaptchaEnabled) {
            return;
        }
        if (!captchaUtils.verifyCaptcha(captchaToken)) {
            throw new BadRequestException("Invalid captcha token");
        }
    }

    private void checkIfUserExists(User user) {
        if (user == null) {
            throw new NotFoundException("User not found");
        }
    }
}
