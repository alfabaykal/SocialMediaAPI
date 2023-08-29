package com.alfabaykal.socialmediaapi.repository;

import com.alfabaykal.socialmediaapi.model.ChatRequest;
import com.alfabaykal.socialmediaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRequestRepository extends JpaRepository<ChatRequest, Long> {
    boolean existsBySenderAndReceiver(User sender, User receiver);
}
