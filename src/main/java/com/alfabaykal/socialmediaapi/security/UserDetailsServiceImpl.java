package com.alfabaykal.socialmediaapi.security;

import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.model.User;
import com.alfabaykal.socialmediaapi.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsImpl loadUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return new UserDetailsImpl(user.get());
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String login) {
        Optional<User> user;

        if (EmailValidator.getInstance().isValid(login)) {
            user = userRepository.findByEmail(login);
        } else {
            user = userRepository.findByUsername(login);
        }

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }

        return new UserDetailsImpl(user.get());

    }

}
