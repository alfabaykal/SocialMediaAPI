package com.alfabaykal.socialmediaapi.facade.impl;

import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.facade.AuthenticationFacade;
import com.alfabaykal.socialmediaapi.service.RegistrationService;
import com.alfabaykal.socialmediaapi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final RegistrationService registrationService;
    private final UserService userService;

    public AuthenticationFacadeImpl(RegistrationService registrationService, UserService userService) {
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @Override
    public void register(UserRegistrationDto userRegistrationDto) {
        registrationService.register(userRegistrationDto);
    }

    @Override
    public Long getUserIdByUsername(String username) {
        return userService.getUserIdByUsername(username);
    }
}
