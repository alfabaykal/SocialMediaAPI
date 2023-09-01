package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.AuthenticationDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.exception.LoginFailedException;
import com.alfabaykal.socialmediaapi.exception.UserNotCreatedException;
import com.alfabaykal.socialmediaapi.facade.AuthenticationFacade;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import com.alfabaykal.socialmediaapi.util.BindingResultConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Вход и регистрация", description = "Методы для регистрации и аутентификации")
@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;
    private final BindingResultConverter bindingResultConverter;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationFacade authenticationFacade,
                                    BindingResultConverter bindingResultConverter,
                                    JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.authenticationFacade = authenticationFacade;
        this.bindingResultConverter = bindingResultConverter;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> performRegistration(@RequestBody @Valid UserRegistrationDto userRegistrationDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserNotCreatedException("User creation failed: " + bindingResultConverter.convertBindingResultToString(bindingResult));
        }

        authenticationFacade.register(userRegistrationDto);

        Long id = authenticationFacade.getUserIdByUsername(userRegistrationDto.getUsername());

        return Map.of("jwt-token", jwtUtil.generateToken(id, userRegistrationDto.getUsername()));

    }

    @Operation(summary = "Вход для зарегистрированных пользователей")
    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody @Valid AuthenticationDto authenticationDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new LoginFailedException("Login failed: " + bindingResultConverter.convertBindingResultToString(bindingResult));
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials");
        }

        String username = authenticationDto.getUsername();
        Long id = authenticationFacade.getUserIdByUsername(username);

        return Map.of("jwt-token", jwtUtil.generateToken(id, authenticationDto.getUsername()));

    }

}
