package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.AuthenticationDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.exception.LoginFailedException;
import com.alfabaykal.socialmediaapi.exception.UserNotCreatedException;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import com.alfabaykal.socialmediaapi.service.RegistrationService;
import com.alfabaykal.socialmediaapi.service.UserService;
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

@Tag(name = "Вход и регистрация", description = "Методы для регистрации и утентификации")
@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final RegistrationService registrationService;
    private final BindingResultConverter bindingResultConverter;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationController(RegistrationService registrationService,
                                    BindingResultConverter bindingResultConverter,
                                    JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                                    UserService userService) {
        this.registrationService = registrationService;
        this.bindingResultConverter = bindingResultConverter;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> performRegistration(@RequestBody @Valid UserRegistrationDto userRegistrationDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserNotCreatedException("User creation failed: " + bindingResultConverter.convertBindingResultToString(bindingResult));
        }

        registrationService.register(userRegistrationDto);

        Long id = userService.getIdByUsername(userRegistrationDto.getUsername());

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
        Long id = userService.getIdByUsername(username);

        return Map.of("jwt-token", jwtUtil.generateToken(id, authenticationDto.getUsername()));

    }

}
