package com.alfabaykal.socialmediaapi.repository;

import com.alfabaykal.socialmediaapi.model.ChatRequest;
import com.alfabaykal.socialmediaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRequestRepository extends JpaRepository<ChatRequest, Long> {

    boolean existsBySenderAndReceiver(User sender, User receiver);

    Optional<ChatRequest> findBySenderAndReceiver(User sender, User receiver);

}
