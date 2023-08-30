package com.alfabaykal.socialmediaapi.facade;

import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface UserFacade {

    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);

    UserDto updateUser(Long id, UserRegistrationDto userRegistrationDto);

    void deleteUser(Long id);

}
