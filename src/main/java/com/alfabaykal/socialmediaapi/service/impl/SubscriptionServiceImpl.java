package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.model.User;
import com.alfabaykal.socialmediaapi.service.SubscriptionService;
import com.alfabaykal.socialmediaapi.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserService userService;

    public SubscriptionServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void subscribe(Long subscriberId, Long publisherId) {
        User subscriber = userService.getUserByIdWithSubscriptions(subscriberId)
                .orElseThrow(() -> new UserNotFoundException(subscriberId));
        User publisher = userService.getUserByIdWithSubscribers(publisherId)
                .orElseThrow(() -> new UserNotFoundException(publisherId));

        subscriber.getSubscriptions().add(publisher);

        userService.save(subscriber);
    }

    @Transactional
    public void unsubscribe(Long subscriberId, Long publisherId) {
        User subscriber = userService.getUserByIdWithSubscriptions(subscriberId)
                .orElseThrow(() -> new UserNotFoundException(subscriberId));
        User publisher = userService.getUserByIdWithSubscribers(publisherId)
                .orElseThrow(() -> new UserNotFoundException(publisherId));

        subscriber.getSubscriptions().remove(publisher);

        userService.save(subscriber);
    }
}
