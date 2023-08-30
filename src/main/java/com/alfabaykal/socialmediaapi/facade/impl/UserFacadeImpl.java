package com.alfabaykal.socialmediaapi.facade.impl;

import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.facade.UserFacade;
import com.alfabaykal.socialmediaapi.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userService.getUserById(id);
    }

    @Override
    public UserDto updateUser(Long id, UserRegistrationDto userRegistrationDto) {
        return userService.updateUser(id, userRegistrationDto);
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
