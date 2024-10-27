//package com.example.bugtracker_backend.exceptions;
//
//import com.example.bugtracker_backend.utils.JwtRequestFilter;
//import com.example.bugtracker_backend.utils.JwtUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(GlobalExceptionHandler.class)
//public class GlobalExceptionHandlerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private JwtUtils jwtUtils; // Mock JwtUtils dependency
//
//    @MockBean
//    private JwtRequestFilter jwtRequestFilter;
//
//    @MockBean
//    private SecurityFilterChain securityFilterChain;
//
//
//    @BeforeEach
//    void setup(WebApplicationContext context) {
//        MockitoAnnotations.openMocks(this);
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(SecurityMockMvcConfigurers.springSecurity()) // Apply Spring Security config
//                .build();
//    }
//
//
//    @Test
//    @WithMockUser
//        // Mock user authentication
//    void testHandleValidationExceptions() throws Exception {
//        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
//                        .content("{}") // Empty request body to trigger validation
//                        .with(request -> {
//                            request.setAttribute("_csrf", null);
//                            return request;
//                        })) // Disable CSRF
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Validation Failed"))
//                .andExpect(jsonPath("$.errors[0]").exists()); // Check for error message presence
//    }
//
//    @Test
//    @WithMockUser
//        // Mock user authentication
//    void testHandleBadRequestException() throws Exception {
//        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\": \"nonexistent@example.com\", \"password\": \"wrongpassword\"}")
//                        .with(request -> {
//                            request.setAttribute("_csrf", null);
//                            return request;
//                        })) // Disable CSRF
//                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("User not found"));
//    }
//
//    @Test
//    @WithMockUser
//        // Mock user authentication
//    void testHandleConflictException() throws Exception {
//        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\": \"duplicate@example.com\", \"password\": \"password123\"}")
//                        .with(request -> {
//                            request.setAttribute("_csrf", null);
//                            return request;
//                        })) // Disable CSRF
//                .andExpect(status().isConflict()).andExpect(jsonPath("$.message").value("Email is already in use"));
//    }
//
//    @Test
//    @WithMockUser
//        // Mock user authentication
//    void testHandleGenericException() throws Exception {
//        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
//                        .content("{}")
//                        .with(request -> {
//                            request.setAttribute("_csrf", null);
//                            return request;
//                        })) // Disable CSRF
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$").value("An error occurred: Unexpected character ('i' (code 105))"));
//    }
//}
