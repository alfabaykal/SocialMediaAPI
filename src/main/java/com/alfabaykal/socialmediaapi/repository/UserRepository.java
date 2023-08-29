package com.alfabaykal.socialmediaapi.repository;

import com.alfabaykal.socialmediaapi.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Long findIdByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "friends")
    Optional<User> findWithFriendsById(Long id);

    @EntityGraph(attributePaths = "subscriptions")
    Optional<User> findWithSubscriptionsById(Long id);

    @EntityGraph(attributePaths = "subscribers")
    Optional<User> findWithSubscribersById(Long id);

    @EntityGraph(attributePaths = "sentFriendRequests")
    Optional<User> findWithSentFriendRequestsById(Long id);

    @EntityGraph(attributePaths = "receivedFriendRequests")
    Optional<User> findWithReceivedFriendRequestsById(Long id);

    @EntityGraph(attributePaths = {"subscriptions", "sentFriendRequests"})
    Optional<User> findWithSubscriptionsAndSentFriendRequestsById(Long id);

    @EntityGraph(attributePaths = {"subscribers", "receivedFriendRequests"})
    Optional<User> findWithSubscribersAndReceivedFriendRequestsById(Long id);

    @EntityGraph(attributePaths = {"subscribers", "sentFriendRequests", "friends"})
    Optional<User> findWithSubscribersAndSentFriendRequestsAndFriendsById(Long id);

    @EntityGraph(attributePaths = {"subscriptions", "receivedFriendRequests", "friends"})
    Optional<User> findWithSubscriptionsAndReceivedFriendRequestsAndFriendsById(Long id);

    @EntityGraph(attributePaths = {"subscriptions", "friends"})
    Optional<User> findWithSubscriptionsAndFriendsById(Long id);
}
