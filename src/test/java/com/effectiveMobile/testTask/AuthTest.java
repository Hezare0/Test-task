package com.effectiveMobile.testTask;

import com.effectiveMobile.testTask.DTO.authenticationDTO.JwtAuthenticationResponse;
import com.effectiveMobile.testTask.DTO.authenticationDTO.SignInRequest;
import com.effectiveMobile.testTask.DTO.authenticationDTO.SignUpRequest;
import com.effectiveMobile.testTask.Exception.EmailAlreadyExistException;
import com.effectiveMobile.testTask.Exception.EmailNotFoundException;
import com.effectiveMobile.testTask.services.servicesAssociatedUser.AuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authService;

    @Test
    public void testSuccessfulSignUp() throws Exception {
        JwtAuthenticationResponse token = new JwtAuthenticationResponse(generateJwtToken("TestUser@gmail.com"));

        when(authService.signUp(any(SignUpRequest.class))).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"TestUser@gmail.com\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token.getToken()));
    }

    @Test
    public void testEmailExistsSignUp() throws Exception {

        when(authService.signUp(any(SignUpRequest.class))).thenThrow(new EmailAlreadyExistException("Пользователь с таким email уже существует"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"TestUser@gmail.com\", \"password\": \"password\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Пользователь с таким email уже существует"));
    }

    @Test
    public void testSignUpWithInvalidData() throws Exception {

        when(authService.signUp(any(SignUpRequest.class))).thenThrow(new IllegalArgumentException("Адрес электронной почты должен содержать от 5 до 255 символов"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"\", \"password\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value(anyOf(
                        is("Пароль не может быть пустыми" ),
                        is("Длина пароля должна быть от 8 до 255 символов")
                )))
                .andExpect(jsonPath("$.email").value(anyOf(
                        is("Адрес электронной почты должен содержать от 5 до 255 символов"),
                        is("Адрес электронной почты не может быть пустыми")
                )));
    }

    @Test
    public void testSuccessfulSignIn() throws Exception {
        JwtAuthenticationResponse token = new JwtAuthenticationResponse(generateJwtToken("TestUser@gmail.com"));

        when(authService.signIn(any(SignInRequest.class))).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"TestUser@gmail.com\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token.getToken()));
    }

    @Test
    public void testSignUpWithUserNotFound() throws Exception {

        when(authService.signIn(any(SignInRequest.class))).thenThrow(new EmailNotFoundException("Пользователь не найден"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"TestUser@gmail.com\", \"password\": \"password\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Пользователь не найден"));
    }

    @Test
    public void testSignInWithInvalidData() throws Exception {

        when(authService.signIn(any(SignInRequest.class))).thenThrow(new IllegalArgumentException("Адрес электронной почты должен содержать от 5 до 255 символов"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"\", \"password\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value(anyOf(
                        is("Пароль не может быть пустыми" ),
                        is("Длина пароля должна быть от 8 до 255 символов")
                )))
                .andExpect(jsonPath("$.email").value(anyOf(
                        is("Адрес электронной почты должен содержать от 5 до 255 символов"),
                        is("Адрес электронной почты не может быть пустыми")
                )));
    }


    private String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9")
                .compact();
    }
}
