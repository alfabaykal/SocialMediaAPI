package com.alfabaykal.socialmediaapi.facade.impl;

import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.facade.UserFacade;
import com.alfabaykal.socialmediaapi.service.UserService;
import com.alfabaykal.socialmediaapi.util.EntityDtoConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final EntityDtoConverter entityDtoConverter;

    public UserFacadeImpl(UserService userService, EntityDtoConverter entityDtoConverter) {
        this.userService = userService;
        this.entityDtoConverter = entityDtoConverter;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(entityDtoConverter::convertUserToUserDto).toList();
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userService.getUserById(id)
                .map(entityDtoConverter::convertUserToUserDto);
    }

    @Override
    public UserDto updateUser(Long id, UserRegistrationDto userRegistrationDto) {
        return entityDtoConverter
                .convertUserToUserDto(userService
                        .updateUser(id, entityDtoConverter.convertUserRegistrationDtoToUser(userRegistrationDto)));
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
