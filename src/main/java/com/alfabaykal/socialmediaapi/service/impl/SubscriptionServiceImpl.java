package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.dto.UserRelationshipDto;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.service.SubscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserServiceImpl userServiceImpl;

    public SubscriptionServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Transactional
    public void subscribe(Long subscriberId, Long publisherId) {
        UserRelationshipDto subscriber = userServiceImpl.getUserByIdWithSubscriptions(subscriberId)
                .orElseThrow(() -> new UserNotFoundException(subscriberId));
        UserRelationshipDto publisher = userServiceImpl.getUserByIdWithSubscribers(publisherId)
                .orElseThrow(() -> new UserNotFoundException(publisherId));

        subscriber.getSubscriptions().add(publisher);

        userServiceImpl.save(subscriber);
    }

    @Transactional
    public void unsubscribe(Long subscriberId, Long publisherId) {
        UserRelationshipDto subscriber = userServiceImpl.getUserByIdWithSubscriptions(subscriberId)
                .orElseThrow(() -> new UserNotFoundException(subscriberId));
        UserRelationshipDto publisher = userServiceImpl.getUserByIdWithSubscribers(publisherId)
                .orElseThrow(() -> new UserNotFoundException(publisherId));

        subscriber.getSubscriptions().remove(publisher);

        userServiceImpl.save(subscriber);
    }
}
