package com.alfabaykal.socialmediaapi.facade;

import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;

public interface AuthenticationFacade {

    void register(UserRegistrationDto userRegistrationDto);

    Long getUserIdByUsername(String username);

}
