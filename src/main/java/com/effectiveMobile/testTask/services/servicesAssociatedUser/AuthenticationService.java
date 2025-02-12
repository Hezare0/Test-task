package com.effectiveMobile.testTask.services.servicesAssociatedUser;

import com.effectiveMobile.testTask.DTO.authenticationDTO.JwtAuthenticationResponse;
import com.effectiveMobile.testTask.DTO.authenticationDTO.SignInRequest;
import com.effectiveMobile.testTask.DTO.authenticationDTO.SignUpRequest;
import com.effectiveMobile.testTask.Exception.EmailNotFoundException;
import com.effectiveMobile.testTask.entity.User;
import com.effectiveMobile.testTask.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                UserRole.PERFORMER
        );


        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));
        }catch (BadCredentialsException e) {
            throw new EmailNotFoundException("Пользователь не найден");
        }


        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

}
