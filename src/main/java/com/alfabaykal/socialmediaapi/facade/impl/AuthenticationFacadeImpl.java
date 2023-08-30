package com.alfabaykal.socialmediaapi.facade.impl;

import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.facade.AuthenticationFacade;
import com.alfabaykal.socialmediaapi.service.RegistrationService;
import com.alfabaykal.socialmediaapi.service.UserService;
import com.alfabaykal.socialmediaapi.util.EntityDtoConverter;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final RegistrationService registrationService;
    private final UserService userService;
    private final EntityDtoConverter entityDtoConverter;

    public AuthenticationFacadeImpl(RegistrationService registrationService, UserService userService, EntityDtoConverter entityDtoConverter) {
        this.registrationService = registrationService;
        this.userService = userService;
        this.entityDtoConverter = entityDtoConverter;
    }

    @Override
    public void register(UserRegistrationDto userRegistrationDto) {
        registrationService
                .register(entityDtoConverter.convertUserRegistrationDtoToUser(userRegistrationDto));
    }

    @Override
    public Long getUserIdByUsername(String username) {
        return userService.getUserIdByUsername(username);
    }
}
