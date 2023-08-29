package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.dto.UserRelationshipDto;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {

    private final UserService userService;

    public SubscriptionService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void subscribe(Long subscriberId, Long publisherId) {
        UserRelationshipDto subscriber = userService.getUserByIdWithSubscriptions(subscriberId)
                .orElseThrow(() -> new UserNotFoundException(subscriberId));
        UserRelationshipDto publisher = userService.getUserByIdWithSubscribers(publisherId)
                .orElseThrow(() -> new UserNotFoundException(publisherId));

        subscriber.getSubscriptions().add(publisher);

        userService.save(subscriber);
    }

    @Transactional
    public void unsubscribe(Long subscriberId, Long publisherId) {
        UserRelationshipDto subscriber = userService.getUserByIdWithSubscriptions(subscriberId)
                .orElseThrow(() -> new UserNotFoundException(subscriberId));
        UserRelationshipDto publisher = userService.getUserByIdWithSubscribers(publisherId)
                .orElseThrow(() -> new UserNotFoundException(publisherId));

        subscriber.getSubscriptions().remove(publisher);

        userService.save(subscriber);
    }
}
