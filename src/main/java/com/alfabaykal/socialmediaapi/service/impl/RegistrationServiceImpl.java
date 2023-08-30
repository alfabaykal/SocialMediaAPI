package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.service.RegistrationService;
import com.alfabaykal.socialmediaapi.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public static final String DEFAULT_ROLE = "ROLE_USER";

    public RegistrationServiceImpl(UserService userService,
                                   PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserRegistrationDto userRegistrationDto) {
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userRegistrationDto.setRole(DEFAULT_ROLE);
        userService.save(userRegistrationDto);
    }
}
