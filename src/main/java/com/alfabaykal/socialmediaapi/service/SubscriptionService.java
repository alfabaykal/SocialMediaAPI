package com.alfabaykal.socialmediaapi.service;

public interface SubscriptionService {

    void subscribe(Long subscriberId, Long publisherId);

    void unsubscribe(Long subscriberId, Long publisherId);

}
