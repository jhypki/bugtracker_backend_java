package com.example.bugtracker_backend.services;

import com.example.bugtracker_backend.models.User;
import com.example.bugtracker_backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService is a service class that implements the
 * UserDetailsService interface
 * to provide custom user authentication logic.
 * 
 * This service is annotated with @Service to indicate that it's a Spring
 * service component.
 * It uses a UserRepository to fetch user details from the database.
 * 
 * The class overrides the loadUserByUsername method to load user details based
 * on the provided email.
 * If the user is not found, it throws a UsernameNotFoundException.
 * 
 * The returned UserDetails object contains the user's email, password hash, and
 * role.
 * If the user has no specific role, it defaults to "USER".
 * 
 * @author
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        String role = (user.getRole() != null) ? user.getRole().name() : "USER";

        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPasswordHash())
                .roles(role)
                .build();
    }

}
