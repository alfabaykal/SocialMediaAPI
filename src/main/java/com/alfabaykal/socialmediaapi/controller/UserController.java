package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.exception.UserNotUpdatedException;
import com.alfabaykal.socialmediaapi.facade.UserFacade;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import com.alfabaykal.socialmediaapi.util.BindingResultConverter;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Пользователи", description = "Методы для управления и просмотра пользовательских аккаунтов")
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserFacade userFacade;
    private final BindingResultConverter bindingResultConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserController(UserFacade userFacade,
                          BindingResultConverter bindingResultConverter,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userFacade = userFacade;
        this.bindingResultConverter = bindingResultConverter;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Получение списка всех пользователей")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userFacade.getAllUsers();
    }

    @Operation(summary = "Просмотр профиля пользователя")
    @GetMapping("/{id}")
    public UserDto getUserById(@Parameter(description = "Идентификатор пользователя")
                               @PathVariable Long id) {
        return userFacade.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Hidden
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id,
                              @RequestBody @Valid UserRegistrationDto user,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserNotUpdatedException("User updating failed: "
                    + bindingResultConverter.convertBindingResultToString(bindingResult));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userFacade.updateUser(id, user);
    }

    @Operation(summary = "Редактировать аккаунт")
    @PutMapping()
    public UserDto updateMyAccount(@Parameter(description = "Bearer header with JWT")
                                   @RequestHeader("Authorization") String jwtHeader,
                                   @RequestBody @Valid UserRegistrationDto user,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserNotUpdatedException("User updating failed: "
                    + bindingResultConverter.convertBindingResultToString(bindingResult));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userFacade.updateUser(jwtUtil.getUserIdByJwtHeader(jwtHeader), user);
    }

    @Hidden
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
    }

    @Operation(summary = "Удаление профиля")
    @DeleteMapping()
    public void deleteMyAccount(@Parameter(description = "Bearer header with JWT")
                                @RequestHeader("Authorization") String jwtHeader) {
        userFacade.deleteUser(jwtUtil.getUserIdByJwtHeader(jwtHeader));
    }

}
